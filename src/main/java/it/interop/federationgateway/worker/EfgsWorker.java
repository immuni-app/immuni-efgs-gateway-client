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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bouncycastle.cms.CMSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		log.info("START Processing upload.");
		//Recupero ultimo upload (ultima data e ultimo batchtag associato al file)
		EfgsWorkerInfo efgsWorkerInfo = efgsWorkerInfoRepository.getEfgsWorkerInfoUpload();
		
		if (efgsWorkerInfo.isBatchDateBeforeToDay()) {
			efgsWorkerInfo.setBatchDate(EfgsWorkerInfo.getToDayBatchDate());
			efgsWorkerInfo.setBatchTag(EfgsWorkerInfo.getToDayDefaultBatchTag());
		}
		
		String batchDate = efgsWorkerInfo.getBatchDate();
		String batchTag = efgsWorkerInfo.getNextBatchTag();
		
		try {
			List<BatchFile> batchFilesToSend = batchFileRepository.getIdBatchFilesToSend();
			
			if (batchFilesToSend != null) {
				log.info("Upload INFO -> total number of batches to be send: "+batchFilesToSend.size());
				for (BatchFile idBatchFile: batchFilesToSend) {
					
					String nextBatchTag = upload(idBatchFile.getId(), batchDate, batchTag);

					batchTag = nextBatchTag;
				}
			}
			
		} catch (Exception e) {
			log.error("ERROR Processing upload.", e);
		}
		log.info("END Processing upload.");
	}
	
//	@Scheduled(cron = "${efgs.worker.download.schedul}")
//	@SchedulerLock(name = "EfgsWorker_downloadWorker")
	public void downloadWorker() {
		log.info("START Processing download.");
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
			processUploadEuRaw(idUploadEuRaw.getId());
		}
		
		log.info("END Processing download.");
	}

	@Scheduled(cron = "${efgs.worker.delete.schedul}")
	@SchedulerLock(name = "EfgsWorker_deleteWorker")
	public void deleteOldDateWorker() {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -Integer.parseInt(dataRetentionDays));
		uploadUeRawRepository.deleteOldData(calendar.getTime());
	}
	
	
	@Transactional
	private String upload(String id, String batchDate, String batchTag) {
		log.info("Upload INFO before sending -> batchDate: {} - batchTag: {} - batch id: {}", batchDate, batchTag, id);
		String nextBatchTag = null;
		try {
			
			BatchFile batchFile = batchFileRepository.getById(id);
			
			batchFile.setOrigin(originCountry);

			log.info("Upload INFO number of keys:"+batchFile.getKeys().size());

			List<EfgsKey> keyEntities = DiagnosisKeyMapper.entityToEfgsKeys(batchFile);

			
			EfgsProto.DiagnosisKeyBatch protoBatch = EfgsProto.DiagnosisKeyBatch.newBuilder()
		    	      .addAllKeys(DiagnosisKeyMapper.efgsKeyToProto(keyEntities))
		    	      .build();
			log.info("Upload INFO generated protobuf to upload");

		    
	        String batchSignature = signatureGenerator.getSignatureForBytes(
	                BatchSignatureUtils.generateBytesToVerify(protoBatch));
			log.info("Upload INFO signed protobuf to upload");

	        
	        RestApiResponse<String> esito = client.upload(batchTag, batchSignature, protoBatch);
	        batchFile.setBatchTag(batchTag);
	        batchFile.setUploadEuReport(esito.getData());
			log.info("Upload INFO uploaded protobuf");
	        
			if (esito.getStatusCode() == RestApiClient.UPLOAD_STATUS_CREATED_201 
					|| esito.getStatusCode() == RestApiClient.UPLOAD_STATUS_WARNING_207) {
			
				batchFileRepository.setBatchTag(batchFile);
				log.info("Upload INFO batch file marked as sent.");

			}
	    	efgsLogRepository.save(
	    			EfgsLog.buildUploadEfgsLog(originCountry, batchTag, 
	    					Long.valueOf(batchFile.getKeys().size()), 0l, batchSignature, esito.getData())
	    			);

			efgsWorkerInfoRepository.saveUploadBatchTag(batchDate, batchTag);

		} catch (RestApiException | BatchSignatureException | CMSException | IOException e) {
			log.error("ERROR Processing upload.", e);
		}
		log.info("Upload INFO after sending -> batchDate: {} - batchTag: {} - batch id: {}", batchDate, batchTag, id);
		return nextBatchTag;
	}

	@Transactional
	private String download(String batchDate, String batchTag) throws Exception {
		log.info("Download INFO before sending -> batchDate: {} - batchTag: {}", batchDate, batchTag);
		String nextBatchTag = null;
		try {
			RestApiResponse<EfgsProto.DiagnosisKeyBatch> resp = client.download(batchDate, batchTag);

			if (resp.getStatusCode() == RestApiClient.DOWNLOAD_STATUS_RETURNS_BATCH_200) {
				log.info("Start processing downloaded keys");
			    nextBatchTag = resp.getNextBatchTag();

			    String batchTagFound = resp.getBatchTag();

			    if (resp.getData() != null) {
					
					EfgsProto.DiagnosisKeyBatch protoDiagnosisKeyBatch = resp.getData();

					RestApiResponse<List<Audit>> respAudit = client.audit(batchDate, batchTagFound);
					
					List<Audit> auditList = respAudit.getData();
					
				    Map<String, List<EfgsKey>> efgsKeyPerOriginMap = DiagnosisKeyMapper.protoToEfgsKeyPerOrigin(protoDiagnosisKeyBatch.getKeysList());
					log.info("Breakdown of UE keys by origin to verify the signature");
				    
				    for (Audit audit: auditList) {
				    	List<EfgsKey> efgsKeyPerOrigin = efgsKeyPerOriginMap.get(audit.getCountry());
				    	
				    	boolean verifiedSign = batchSignatureVerifier.validateDiagnosisKeyWithSignature(efgsKeyPerOrigin, audit);
						log.info("Signature verified: {}", verifiedSign);
				    	
				    	UploadEuRaw diagnosisKeyEntity = DiagnosisKeyMapper.efgsKeysToEntity(efgsKeyPerOrigin, batchTagFound);
				    	
				    	diagnosisKeyEntity.setVerifiedSign(verifiedSign);
				    	
				    	uploadUeRawRepository.save(diagnosisKeyEntity);
						log.info("Saved raw key data");
				    	
				    	efgsLogRepository.save(
				    			EfgsLog.buildDownloadEfgsLog(audit.getCountry(), batchTagFound, 
				    					Long.valueOf(diagnosisKeyEntity.getKeys().size()), diagnosisKeyEntity.getInvalid(), verifiedSign, "OK", audit)
				    			);

				    }
				}
				efgsWorkerInfoRepository.saveDownloadBatchTag(batchDate, batchTagFound);
				log.info("End processing downloaded keys");
			} else {
				log.info("Warning! Response code: {}", resp.getStatusCode().toString());
				throw new Exception(resp.getStatusCode().toString());
			}

		} catch (Exception e) {
			log.error("ERROR Processing download.", e);
			return null;
		}
		log.info("Download INFO after sending -> batchDate: {} - batchTag: {}", batchDate, batchTag);
		return nextBatchTag;
	}

	@Transactional
	public void processUploadEuRaw(String id) {
		log.info("START processing raw keys data -> id: {}", id);
		UploadEuRaw uploadEuRaw = uploadUeRawRepository.getById(id);
		
		Map<String, UploadEu> mapUploadEuPerCountry = DiagnosisKeyMapper.splitKeysPerVisitatedCountry(uploadEuRaw);
		log.info("Breakdown of UE keys by country");
		
		Map<String, Long> ammountPerCountry = new HashMap<String, Long>();
		for (UploadEu uploadEu : mapUploadEuPerCountry.values()) {
			ammountPerCountry.put(uploadEu.getCountry(), Long.valueOf(uploadEu.getKeys().size()));
			uploadUeRepository.save(uploadEu);
			log.info("Saved EU keys in order to produce the batch files");
		}
		
		uploadUeRawRepository.setProcessed(id);

		efgsLogRepository.setStaristicByCountryBatchtag(uploadEuRaw.getOrigin(), uploadEuRaw.getBatchTag(), ammountPerCountry);
		log.info("END processing raw keys data -> id: {}", id);
	}
	
	
}
