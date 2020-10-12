package it.interop.federationgateway.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import it.interop.federationgateway.entity.EfgsLog;

@Repository
public class EfgsLogRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	public EfgsLog save(EfgsLog efgsWorkerInfo) {
		return mongoTemplate.save(efgsWorkerInfo);
	}
	
	public EfgsLog getByCountryBatchtag(String country, String batchTag) {
		Query query = new Query();
		query.addCriteria(Criteria.where("country").is(country)).addCriteria(Criteria.where("batchTag").is(batchTag));
		return mongoTemplate.findOne(query, EfgsLog.class);
	}
	
	public void setStaristicByCountryBatchtag(String country, String batchTag, Map<String, Long> ammountPerCountry) {
		EfgsLog efgsLog = getByCountryBatchtag(country, batchTag);
		if (efgsLog!=null) {
			efgsLog.setAmmountPerCountry(ammountPerCountry);
			save(efgsLog);
		}
	}
	
}
