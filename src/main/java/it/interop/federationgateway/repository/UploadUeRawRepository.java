package it.interop.federationgateway.repository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import it.interop.federationgateway.entity.UploadEuRaw;

@Repository
public class UploadUeRawRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	public UploadEuRaw save(UploadEuRaw uploadEuRaw) {
		return mongoTemplate.save(uploadEuRaw);
	}

	
	public UploadEuRaw getById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		UploadEuRaw uploadEuRaw = mongoTemplate.findOne(query, UploadEuRaw.class);
		return uploadEuRaw;
	}
	
	public void setProcessed(String id) {
		Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
		Update update = new Update();
		update.set("to_process", false);
		mongoTemplate.findAndModify(query, update, UploadEuRaw.class);
	}
	
	public List<UploadEuRaw> getIdUploadEuRawToProcess() {
		Query query = new Query();
		query.addCriteria(Criteria.where("to_process").is(true)).addCriteria(Criteria.where("verified_sign").is(true));
		query.fields().include("_id");
		List<UploadEuRaw> uploadEuRaw = mongoTemplate.find(query, UploadEuRaw.class);
		return uploadEuRaw;
	}
	
	public void deleteOldData(Date date) {
		Query query = new Query();
		query.addCriteria(Criteria.where("created_date").lt(date));
		mongoTemplate.findAndRemove(query, UploadEuRaw.class);
	}
	
}
