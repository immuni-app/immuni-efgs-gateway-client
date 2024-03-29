/*-
 *   Copyright (C) 2020 Presidenza del Consiglio dei Ministri.
 *   Please refer to the AUTHORS file for more information. 
 *   This program is free software: you can redistribute it and/or modify 
 *   it under the terms of the GNU Affero General Public License as 
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *   This program is distributed in the hope that it will be useful, 
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of 
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 *   GNU Affero General Public License for more details.
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program. If not, see <https://www.gnu.org/licenses/>.   
 */
package it.interop.federationgateway.worker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bouncycastle.cms.CMSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import it.interop.federationgateway.batchsigning.BatchSignatureException;
import it.interop.federationgateway.batchsigning.BatchSignatureUtils;
import it.interop.federationgateway.batchsigning.BatchSignatureVerifier;
import it.interop.federationgateway.batchsigning.SignatureGenerator;
import it.interop.federationgateway.client.RestApiClient;
import it.interop.federationgateway.client.base.RestApiException;
import it.interop.federationgateway.client.base.RestApiResponse;
import it.interop.federationgateway.entity.BatchFile;
import it.interop.federationgateway.entity.EfgsLog;
import it.interop.federationgateway.entity.EfgsWorkerInfo;
import it.interop.federationgateway.entity.UploadEu;
import it.interop.federationgateway.entity.UploadEuRaw;
import it.interop.federationgateway.mapper.DiagnosisKeyMapper;
import it.interop.federationgateway.model.Audit;
import it.interop.federationgateway.model.EfgsKey;
import it.interop.federationgateway.model.EfgsProto;
import it.interop.federationgateway.repository.BatchFileRepository;
import it.interop.federationgateway.repository.EfgsLogRepository;
import it.interop.federationgateway.repository.EfgsWorkerInfoRepository;
import it.interop.federationgateway.repository.UploadUeRawRepository;
import it.interop.federationgateway.repository.UploadUeRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

@Slf4j
@Service
public class EfgsWorker {

	@Getter
	@Value("${efgs.origin_country}")
	private String originCountry;
	
	@Value("${efgs.data_retention_days}")
	private String dataRetentionDays;

	@Autowired(required=true)
	private RestApiClient client;

	@Autowired(required=true)
	private UploadUeRawRepository uploadUeRawRepository;
	
	@Autowired(required=true)
	private UploadUeRepository uploadUeRepository;
	
	@Autowired(required=true)
	private BatchFileRepository batchFileRepository;

	@Autowired(required=true)
	private EfgsWorkerInfoRepository efgsWorkerInfoRepository;

	@Autowired(required=true)
	private EfgsLogRepository efgsLogRepository;

	@Autowired(required=true)
	private SignatureGenerator signatureGenerator;

	@Autowired(required=true)
	private BatchSignatureVerifier batchSignatureVerifier;
	
	@Scheduled(cron = "${efgs.worker.upload.schedul}")
	@SchedulerLock(name = "EfgsWorker_uploadWorker")
	public void uploadWorker() {
		log.info("@@@  UPLOAD -> START Processing upload. @@@");
		//Recupero ultimo upload (ultima data e ultimo batchtag associato al file)
		EfgsWorkerInfo efgsWorkerInfo = efgsWorkerInfoRepository.getEfgsWorkerInfoUpload();
		
		if (efgsWorkerInfo.isBatchDateBeforeToDay()) {
			efgsWorkerInfo.setBatchDate(EfgsWorkerInfo.getToDayBatchDate());
			efgsWorkerInfo.setBatchTag(EfgsWorkerInfo.getToDayDefaultBatchTag());
			efgsWorkerInfoRepository.save(efgsWorkerInfo);
		}
		
		
		List<BatchFile> batchFilesToSend = batchFileRepository.getIdBatchFilesToSend();
		
		if (batchFilesToSend != null) {
			log.info("Upload INFO -> total number of batches to be send: "+batchFilesToSend.size());
			for (BatchFile idBatchFile: batchFilesToSend) {
				try {
					String batchDate = efgsWorkerInfo.getBatchDate();
					String batchTag = efgsWorkerInfo.getNextBatchTag();
					upload(idBatchFile.getId(), batchDate, batchTag);
					efgsWorkerInfo.setBatchTag(batchTag);
				} catch (Exception e) {
					log.error("ERROR Processing upload.", e);
				}
			}
		}
			
		log.info("@@@  UPLOAD -> END Processing upload. @@@");
	}
	
	@Scheduled(cron = "${efgs.worker.download.schedul}")
	@SchedulerLock(name = "EfgsWorker_downloadWorker")
	public void downloadWorker() {
		log.info("###  DOWNLOAD -> START Processing download. ###");
		//Recupero ultimo download (ultima data e ultimo batchtag restituito dal gateway)
		EfgsWorkerInfo efgsWorkerInfo = efgsWorkerInfoRepository.getEfgsWorkerInfoDownload();
		
		String batchDate = efgsWorkerInfo.getBatchDate();
		//Calcolo il prossimo batchtag da scaricare
		String batchTag = efgsWorkerInfo.getNextBatchTag();
		
		try {
			while (true) {
				//Effettuo il download del batchFile			
				String nextBatchTag = download(batchDate, batchTag);
				
				if (nextBatchTag == null) {
					//se viene restituito nextBatchTag non valorizzato e sto scaricando i file del girono precedemte passo al giorno successivo
					if (efgsWorkerInfo.isBatchDateBeforeToDay()) {
						efgsWorkerInfo.setBatchDate(efgsWorkerInfo.getNextBatchDate());
						efgsWorkerInfo.setBatchTag(efgsWorkerInfo.getDefaultBatchTag());
						efgsWorkerInfoRepository.save(efgsWorkerInfo);
						batchDate = efgsWorkerInfo.getBatchDate();
						nextBatchTag = efgsWorkerInfo.getNextBatchTag();
					} else {
						break;
					}
				}
	
				batchTag = nextBatchTag;
			}
			
		} catch (Exception e) {
			log.error("ERROR Processing download.", e);
		}

		List<UploadEuRaw> idUploadEuRawToProcess = uploadUeRawRepository.getIdUploadEuRawToProcess();
		
		for (UploadEuRaw idUploadEuRaw: idUploadEuRawToProcess) {
			processUploadEuRaw(idUploadEuRaw.getId(), efgsWorkerInfo.getSkippedCounties());
		}
		
		log.info("###  DOWNLOAD -> END Processing download. ####");
	}

	@Scheduled(cron = "${efgs.worker.delete.schedul}")
	@SchedulerLock(name = "EfgsWorker_deleteWorker")
	public void deleteOldDataWorker() {
		log.info("§§§ DELETE -> START Processing delete old data. §§§");
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -Integer.parseInt(dataRetentionDays));
		uploadUeRawRepository.deleteOldData(calendar.getTime());
		log.info("§§§ DELETE -> END Processing delete old data. §§§");
	}
	
	
	@Transactional
	private void upload(String id, String batchDate, String batchTag) {
		log.info("Upload INFO before sending -> batchDate: {} - batchTag: {} - batch id: {}", batchDate, batchTag, id);
		try {
			
			BatchFile batchFile = batchFileRepository.getById(id);
			
			batchFile.setOrigin(originCountry);

			List<EfgsKey> keyEntities = DiagnosisKeyMapper.entityToEfgsKeys(batchFile);

			log.info("Upload INFO -> batchDate: {} - batchTag: {} - batch id: {} - number of keys: {} - keys valid: {}", batchDate, batchTag, id, batchFile.getKeys().size(), keyEntities.size());

			if (keyEntities != null && keyEntities.size() > 0) {
				EfgsProto.DiagnosisKeyBatch protoBatch = EfgsProto.DiagnosisKeyBatch.newBuilder()
			    	      .addAllKeys(DiagnosisKeyMapper.efgsKeyToProto(keyEntities))
			    	      .build();
				log.info("Upload INFO generated protobuf to upload. -> batchDate: {} - batchTag: {} - batch id: {}", batchDate, batchTag, id);
	
			    
		        String batchSignature = signatureGenerator.getSignatureForBytes(
		                BatchSignatureUtils.generateBytesToVerify(protoBatch));
				log.info("Upload INFO signed protobuf to upload. -> batchDate: {} - batchTag: {} - batch id: {}", batchDate, batchTag, id);
	
				String report = null;
				HttpStatus statusCode = null;
				try {
			        RestApiResponse<String> esito = client.upload(batchTag, batchSignature, protoBatch);
			        report = esito.getData();
			        statusCode = esito.getStatusCode();
					log.info("Upload INFO uploaded protobuf. -> batchDate: {} - batchTag: {} - batch id: {} - esito: {} - ", batchDate, batchTag, id, report);
				} catch (HttpClientErrorException e) {
					log.error("ERROR Processing upload HttpClientErrorException. -> batchDate: {} - batchTag: {} - batch id: {} - Code: {} - Message: {}", 
							batchDate, batchTag, id, e.getRawStatusCode(), e.getMessage(),  e);
					if (e.getRawStatusCode() == 400) {
						statusCode = RestApiClient.UPLOAD_STATUS_BAD_REQUEST_400;
						report = e.getMessage();
					} else if (e.getRawStatusCode() == 409) {
						statusCode = RestApiClient.UPLOAD_STATUS_BATCHTAG_ALREADY_EXIST_409;
						report = e.getMessage();
					} else {
						throw e;
					}
				}
		        
		        
				if (statusCode == RestApiClient.UPLOAD_STATUS_CREATED_201 
						|| statusCode == RestApiClient.UPLOAD_STATUS_WARNING_207
						|| statusCode == RestApiClient.UPLOAD_STATUS_INVALID_SIGNATURE_400
						|| statusCode == RestApiClient.UPLOAD_STATUS_BAD_REQUEST_400
						|| statusCode == RestApiClient.UPLOAD_STATUS_INVALID_CONTENT_406) {
	
			        batchFile.setBatchTag(batchTag);
	
					batchFileRepository.setBatchTag(batchFile);
					log.info("Upload INFO batch file marked as sent. -> batchDate: {} - batchTag: {} - batch id: {}", batchDate, batchTag, id);
				}
				
		    	efgsLogRepository.save(
		    			EfgsLog.buildUploadEfgsLog(originCountry, batchTag, 
		    					Long.valueOf(keyEntities.size()), Long.valueOf(batchFile.getKeys().size()-keyEntities.size()), batchSignature, report)
		    			);
				log.info("Upload INFO saved log. -> batchDate: {} - batchTag: {} - batch id: {}", batchDate, batchTag, id);
	
				efgsWorkerInfoRepository.saveUploadBatchTag(batchDate, batchTag);
				log.info("Upload INFO saved worker info. -> batchDate: {} - batchTag: {} - batch id: {}", batchDate, batchTag, id);
			} else {
		        batchFile.setBatchTag("EMPTY-BATCH");
				batchFileRepository.setBatchTag(batchFile);
				log.info("Upload INFO not uploaded: empty ke -> batch id: {} - esito: Empty keys - ", id);
			}

		} catch (RestApiException e) {
			log.error("ERROR Processing upload RestApiException. -> batchDate: {} - batchTag: {} - batch id: {}", batchDate, batchTag, id, e);
		} catch (BatchSignatureException e) {
			log.error("ERROR Processing upload BatchSignatureException. -> batchDate: {} - batchTag: {} - batch id: {}", batchDate, batchTag, id, e);
		} catch (CMSException e) {
			log.error("ERROR Processing upload CMSException. -> batchDate: {} - batchTag: {} - batch id: {}", batchDate, batchTag, id, e);
		} catch (IOException e) {
			log.error("ERROR Processing upload IOException. -> batchDate: {} - batchTag: {} - batch id: {}", batchDate, batchTag, id, e);
		}
		log.info("Upload INFO after sending -> batchDate: {} - batchTag: {} - batch id: {}", batchDate, batchTag, id);
	}

	@Transactional
	private String download(String batchDate, String batchTag) throws Exception {
		log.info("Download INFO before sending -> batchDate: {} - batchTag: {}", batchDate, batchTag);
		String nextBatchTag = null;
		try {
			RestApiResponse<EfgsProto.DiagnosisKeyBatch> resp = client.download(batchDate, batchTag);

			if (resp.getStatusCode() == RestApiClient.DOWNLOAD_STATUS_RETURNS_BATCH_200) {
				log.info("Start processing downloaded keys -> batchDate: {} - batchTag: {}", batchDate, batchTag);
			    nextBatchTag = resp.getNextBatchTag();

			    String batchTagFound = resp.getBatchTag();

			    RestApiResponse<List<Audit>> respAudit = client.audit(batchDate, batchTagFound);
				List<Audit> auditList = respAudit.getData();

			    if (resp.getData() != null) {
					
					EfgsProto.DiagnosisKeyBatch protoDiagnosisKeyBatch = resp.getData();

				    Map<String, List<EfgsKey>> efgsKeyPerOriginMap = DiagnosisKeyMapper.protoToEfgsKeyPerOrigin(protoDiagnosisKeyBatch.getKeysList());
					log.info("Breakdown of UE keys by origin to verify the signature");
					
					Map<String, List<Audit>> auditToMapPerCounty = auditToMapPerCounty(auditList);
					
					for (String country : auditToMapPerCounty.keySet()) {
						List<Audit> auditListInner =  auditToMapPerCounty.get(country);
						if (!country.equalsIgnoreCase(originCountry)) {
					    	List<EfgsKey> efgsKeyPerOrigin = efgsKeyPerOriginMap.get(country);
					    	int startIndex = 0;
					    	int count = 0;
						    for (Audit audit: auditListInner) {
						    	count = count + 1;
						    	List<EfgsKey> efgsKeyPerOriginInner = new ArrayList<EfgsKey>();
						    	for (int index=startIndex; index<efgsKeyPerOrigin.size(); index++) {
						    		efgsKeyPerOriginInner.add(efgsKeyPerOrigin.get(index));
						    		if (efgsKeyPerOriginInner.size()>=audit.getAmount()) {
						    			startIndex = index + 1;
						    			break;
						    		}
						    		
						    	}
						    	
						    	boolean verifiedSign = batchSignatureVerifier.validateDiagnosisKeyWithSignature(efgsKeyPerOriginInner, audit);
								log.info("Signature verified: {} - batchDate: {} - batchTag: {} - country: {} - block: {}", 
										verifiedSign, batchDate, batchTag, country, count);
						    	
						    	UploadEuRaw diagnosisKeyEntity = DiagnosisKeyMapper.efgsKeysToEntity(efgsKeyPerOriginInner, batchTagFound, count);
						    	
						    	diagnosisKeyEntity.setVerifiedSign(verifiedSign);
						    	//Not to process if the signature is not verified
						    	diagnosisKeyEntity.setToProcess(verifiedSign);
						    	
						    	uploadUeRawRepository.save(diagnosisKeyEntity);
								log.info("Saved raw key data batchDate: {} - batchTag: {} - country: {} - block: {}", batchDate, batchTag, country, count);
						    	
						    	efgsLogRepository.save(
						    			EfgsLog.buildDownloadEfgsLog(audit.getCountry(), batchTagFound, count,
						    					Long.valueOf(diagnosisKeyEntity.getKeys().size()), diagnosisKeyEntity.getInvalid(), verifiedSign, "TO PROCESS", audit)
						    			);
								log.info("Download INFO saved log. -> batchDate: {} - batchTag: {}", batchDate, batchTag);
							    	
						    }
							
						} else {
							int count = 1;
						    for (Audit audit: auditListInner) {
						    	efgsLogRepository.save(
						    			EfgsLog.buildDownloadEfgsLog(audit.getCountry(), batchTagFound, count++, audit.getAmount(), 0l, true, "EMPTY: ITALIAN BATCH", audit)
						    			);
						    }
							log.info("Download INFO saved log inner. -> batchDate: {} - batchTag: {}", batchDate, batchTag);
						}
					}
				    
				} else {
					int count = 1;
				    for (Audit audit: auditList) {
				    	efgsLogRepository.save(
				    			EfgsLog.buildDownloadEfgsLog(audit.getCountry(), batchTagFound, count++, audit.getAmount(), 0l, true, "EMPTY: ITALIAN BATCH", audit)
				    			);
				    }
					log.info("Download INFO saved log. -> batchDate: {} - batchTag: {}", batchDate, batchTag);
				}
				efgsWorkerInfoRepository.saveDownloadBatchTag(batchDate, batchTagFound);
				log.info("Download INFO worker info.");
				log.info("End processing downloaded keys");
			} else {
				log.info("Warning! Response code: {} - batchDate: {} - batchTag: {}", resp.getStatusCode().toString(), batchDate, batchTag);
				throw new Exception(resp.getStatusCode().toString());
			}

		} catch (Exception e) {
			if (e instanceof HttpClientErrorException && ((HttpClientErrorException)e).getRawStatusCode() == 404) {
				log.error("ERROR Processing download. -> batchDate: {} - batchTag: {} - {}", batchDate, batchTag, e.getMessage());
			} else {
				log.error("ERROR Processing download. -> batchDate: {} - batchTag: {}", batchDate, batchTag, e);
			}
			return null;
		}
		log.info("Download INFO after sending -> batchDate: {} - batchTag: {}", batchDate, batchTag);
		return nextBatchTag;
	}

	
	private Map<String, List<Audit>> auditToMapPerCounty(List<Audit> audits) {
		Map<String, List<Audit>> auditToMapPerCounty = new HashMap<String, List<Audit>>();
		for (Audit audit : audits) {
			if (!auditToMapPerCounty.containsKey(audit.getCountry())) {
				auditToMapPerCounty.put(audit.getCountry(), new ArrayList<Audit>());
			}
			auditToMapPerCounty.get(audit.getCountry()).add(audit);
		}
		
		return auditToMapPerCounty;
	}
	
	@Transactional
	public void processUploadEuRaw(String id, List<String> skippedCountries) {
		log.info("START processing raw keys data -> id: {}", id);
		UploadEuRaw uploadEuRaw = uploadUeRawRepository.getById(id);
		
		Map<String, UploadEu> mapUploadEuPerCountry = DiagnosisKeyMapper.splitKeysPerVisitatedCountry(uploadEuRaw);
		log.info("Breakdown of UE keys by country");
		
		Map<String, Long> ammountPerCountry = new HashMap<String, Long>();
		String processStatus = "TO PROCESS";
		
		if (skippedCountries != null && skippedCountries.contains(uploadEuRaw.getOrigin())) {
			processStatus = "SKIPPED";
			for (UploadEu uploadEu : mapUploadEuPerCountry.values()) {
				ammountPerCountry.put(uploadEu.getCountry(), Long.valueOf(uploadEu.getKeys().size()));
				log.info("SKIPPED - EU keys in order to produce the batch files -> id: {} - country: {} - keys: {}", id, uploadEu.getCountry(), uploadEu.getKeys().size());
			}
		} else {
			processStatus = "PROCESSED";
			for (UploadEu uploadEu : mapUploadEuPerCountry.values()) {
				ammountPerCountry.put(uploadEu.getCountry(), Long.valueOf(uploadEu.getKeys().size()));
				uploadUeRepository.save(uploadEu);
				log.info("PROCESSED - EU keys in order to produce the batch files -> id: {} - country: {} - keys: {}", id, uploadEu.getCountry(), uploadEu.getKeys().size());
			}
		}
		uploadUeRawRepository.setProcessed(id);

		efgsLogRepository.setStaristicByCountryBatchtag(uploadEuRaw.getOrigin(), uploadEuRaw.getBatchTag(), uploadEuRaw.getIndex(), ammountPerCountry, processStatus);
		log.info("END processing raw keys data -> id: {} - status: {}", id, processStatus);
	}
	
	
}
