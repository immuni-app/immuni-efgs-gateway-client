package it.interop.federationgateway.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import it.interop.federationgateway.entity.EfgsLog;

@Repository
public class EfgsLogRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	public EfgsLog save(EfgsLog efgsWorkerInfo) {
		return mongoTemplate.save(efgsWorkerInfo);
	}
	
	
}
