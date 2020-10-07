package it.interop.federationgateway.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import it.interop.federationgateway.entity.UploadEu;

@Repository
public class UploadUeRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	public UploadEu save(UploadEu uploadEu) {
		return mongoTemplate.save(uploadEu);
	}

	
	
}
