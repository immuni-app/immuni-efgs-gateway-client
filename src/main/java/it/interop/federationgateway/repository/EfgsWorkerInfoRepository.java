package it.interop.federationgateway.repository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import it.interop.federationgateway.entity.EfgsWorkerInfo;

@Repository
public class EfgsWorkerInfoRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	public EfgsWorkerInfo save(EfgsWorkerInfo efgsWorkerInfo) {
		return mongoTemplate.save(efgsWorkerInfo);
	}
	
	public EfgsWorkerInfo saveDownloadBatchTag(String batchDate, String batchTag) {
		EfgsWorkerInfo efgsWorkerInfo = getEfgsWorkerInfoDownload();
		efgsWorkerInfo.setBatchDate(batchDate);
		efgsWorkerInfo.setBatchTag(batchTag);
		efgsWorkerInfo.setLastExecution(new Date());
		return mongoTemplate.save(efgsWorkerInfo);
	}
	
	public EfgsWorkerInfo saveUploadBatchTag(String batchDate, String batchTag) {
		EfgsWorkerInfo efgsWorkerInfo = getEfgsWorkerInfoUpload();
		efgsWorkerInfo.setBatchDate(batchDate);
		efgsWorkerInfo.setBatchTag(batchTag);
		efgsWorkerInfo.setLastExecution(new Date());
		return mongoTemplate.save(efgsWorkerInfo);
	}

	public EfgsWorkerInfo getEfgsWorkerInfoUpload() {
		return getEfgsWorkerInfo(EfgsWorkerInfo.OperationType.UPLOAD);
	}

	public EfgsWorkerInfo getEfgsWorkerInfoDownload() {
		return getEfgsWorkerInfo(EfgsWorkerInfo.OperationType.DOWNLOAD);
	}
	
	public EfgsWorkerInfo getEfgsWorkerInfo(EfgsWorkerInfo.OperationType operationType) {
		Query query = new Query();
		query.addCriteria(Criteria.where("operation").is(operationType));
		EfgsWorkerInfo efgsWorkerInfo = mongoTemplate.findOne(query, EfgsWorkerInfo.class);
		
		if (efgsWorkerInfo == null) {
			efgsWorkerInfo = new EfgsWorkerInfo(operationType);
			efgsWorkerInfo = save(efgsWorkerInfo);
		}
		
		return efgsWorkerInfo;
	}
	
	
}
