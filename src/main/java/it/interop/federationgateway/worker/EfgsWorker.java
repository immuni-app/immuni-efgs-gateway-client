package it.interop.federationgateway.worker;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.bouncycastle.cms.CMSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

@Service
public class EfgsWorker {

	@Getter
	@Value("${efgs.origin_country}")
	private String originCountry;

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
		System.out.println("uploadWorker: START");
		//Recupero ultimo upload (ultima data e ultimo batchtag associato al file)
		EfgsWorkerInfo efgsWorkerInfo = efgsWorkerInfoRepository.getEfgsWorkerInfoUpload();
		
		if (efgsWorkerInfo.isBatchDateBeforeToDay()) {
			efgsWorkerInfo.setBatchDate(EfgsWorkerInfo.getToDayBatchDate());
			efgsWorkerInfo.setBatchTag(EfgsWorkerInfo.getDefaultBatchTag());
		}
		
		String batchDate = efgsWorkerInfo.getBatchDate();
		String batchTag = efgsWorkerInfo.getNextBatchTag();
		
		try {
			List<BatchFile> batchFilesToSend = batchFileRepository.getIdBatchFilesToSend();
			
			if (batchFilesToSend != null) {
				for (BatchFile idBatchFile: batchFilesToSend) {
					
					String nextBatchTag = upload(idBatchFile.getId(), batchDate, batchTag);

					batchTag = nextBatchTag;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Scheduled(cron = "${efgs.worker.download.schedul}")
	@SchedulerLock(name = "EfgsWorker_downloadWorker")
	public void downloadWorker() {
		System.out.println("downloadWorker: START");
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
						batchDate = efgsWorkerInfo.getNextBatchDate();
					} else {
						break;
					}
				}
	
				batchTag = nextBatchTag;
			}
			
			List<UploadEuRaw> idUploadEuRawToProcess = uploadUeRawRepository.getIdUploadEuRawToProcess();
			
			for (UploadEuRaw idUploadEuRaw: idUploadEuRawToProcess) {
				processUploadEuRaw(idUploadEuRaw.getId());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Transactional
	private String upload(String id, String batchDate, String batchTag) {
		String nextBatchTag = null;
		try {
			
			BatchFile batchFile = batchFileRepository.getById(id);
			
			batchFile.setOrigin(originCountry);
			
			List<EfgsKey> keyEntities = DiagnosisKeyMapper.entityToEfgsKeys(batchFile);

			
			EfgsProto.DiagnosisKeyBatch protoBatch = EfgsProto.DiagnosisKeyBatch.newBuilder()
		    	      .addAllKeys(DiagnosisKeyMapper.efgsKeyToProto(keyEntities))
		    	      .build();

		    
	        String batchSignature = signatureGenerator.getSignatureForBytes(
	                BatchSignatureUtils.generateBytesToVerify(protoBatch));

	        
	        RestApiResponse<String> esito = client.upload(batchTag, batchSignature, protoBatch);
	        batchFile.setBatchTag(batchTag);
	        batchFile.setUploadEuReport(esito.getData());
	        
			batchFileRepository.setBatchTag(batchFile);

	    	efgsLogRepository.save(
	    			EfgsLog.buildUploadEfgsLog(
	    					batchTag, batchFile.getKeys().size(), batchSignature, esito.getData())
	    			);

			efgsWorkerInfoRepository.saveUploadBatchTag(batchDate, batchTag);

		} catch (RestApiException | BatchSignatureException | CMSException | IOException e) {
			e.printStackTrace();
		}
		return nextBatchTag;
	}

	@Transactional
	private String download(String batchDate, String batchTag) throws Exception {
		String nextBatchTag = null;
		try {
			RestApiResponse<EfgsProto.DiagnosisKeyBatch> resp = client.download(batchDate, batchTag);

			if (resp.getStatusCode() == HttpStatus.OK) {
			    nextBatchTag = resp.getNextBatchTag();

			    String batchTagFound = resp.getBatchTag();

			    if (resp.getData() != null) {
					
					EfgsProto.DiagnosisKeyBatch protoDiagnosisKeyBatch = resp.getData();

					RestApiResponse<List<Audit>> respAudit = client.audit(batchDate, batchTagFound);
					
					List<Audit> auditList = respAudit.getData();
					
				    Map<String, List<EfgsKey>> efgsKeyPerOriginMap = DiagnosisKeyMapper.protoToEfgsKeyPerOrigin(protoDiagnosisKeyBatch.getKeysList());
				    
				    for (Audit audit: auditList) {
				    	List<EfgsKey> efgsKeyPerOrigin = efgsKeyPerOriginMap.get(audit.getCountry());
				    	
				    	boolean verifiedSign = batchSignatureVerifier.validateDiagnosisKeyWithSignature(efgsKeyPerOrigin, audit);
				    	
				    	UploadEuRaw diagnosisKeyEntity = DiagnosisKeyMapper.efgsKeysToEntity(efgsKeyPerOrigin, batchTagFound);
				    	
				    	diagnosisKeyEntity.setVerifiedSign(verifiedSign);
				    	
				    	uploadUeRawRepository.save(diagnosisKeyEntity);
				    	
				    	efgsLogRepository.save(
				    			EfgsLog.buildDownloadEfgsLog(audit.getCountry(), 
				    					batchTagFound, audit.getAmount(), audit.getBatchSignature(), verifiedSign, "OK")
				    			);

				    }
				}
				efgsWorkerInfoRepository.saveDownloadBatchTag(batchDate, batchTagFound);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return nextBatchTag;
	}

	@Transactional
	public void processUploadEuRaw(String id) {
		UploadEuRaw uploadEuRaw = uploadUeRawRepository.getById(id);
		
		Map<String, UploadEu> mapUploadEuPerCountry = DiagnosisKeyMapper.splitKeysPerVisitatedCountry(uploadEuRaw);
		
		for (UploadEu uploadEu : mapUploadEuPerCountry.values()) {
			uploadUeRepository.save(uploadEu);
		}
		
		uploadUeRawRepository.setProcessed(id);
		
	}
	
	
}
