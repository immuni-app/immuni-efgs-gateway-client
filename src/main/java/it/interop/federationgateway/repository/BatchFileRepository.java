package it.interop.federationgateway.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import it.interop.federationgateway.entity.BatchFile;

@Repository
public class BatchFileRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	public BatchFile getById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		BatchFile uploadEuRaw = mongoTemplate.findOne(query, BatchFile.class);
		return uploadEuRaw;
	}

	public List<BatchFile> getIdBatchFilesToSend() {
		Query query = new Query();
		query.addCriteria(Criteria.where("origin").is("IT")).addCriteria(Criteria.where("batch_tag").is(null));
		query.fields().include("_id");
		List<BatchFile> kets = mongoTemplate.find(query, BatchFile.class);
		return kets;
	}

	public void setBatchTag(BatchFile diagnosisKeyBatchFile) {
		Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(diagnosisKeyBatchFile.getId()));
		Update update = new Update();
		update.set("batch_tag", diagnosisKeyBatchFile.getBatchTag()).addToSet("upload_eu_report", diagnosisKeyBatchFile.getUploadEuReport());
		mongoTemplate.findAndModify(query, update, BatchFile.class);
	}

}
