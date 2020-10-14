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
