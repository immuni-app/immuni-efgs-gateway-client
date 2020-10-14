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
