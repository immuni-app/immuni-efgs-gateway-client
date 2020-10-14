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
