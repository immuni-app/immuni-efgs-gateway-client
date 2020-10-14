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
