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
package it.interop.federationgateway.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "upload_eu")
@CompoundIndexes({
    @CompoundIndex(name = "country_1_to_publish_1", def = "{'country' : 1, 'to_publish': 1}")
})
public class UploadEu implements Serializable {

	private static final long serialVersionUID = -9106572163687294395L;

	@Id
	private String id;
	
	@Field("batch_tag")
  	private String batchTag;

	@Field("origin")
	private String origin;

	@Field("country")
	private String country;

	@Field("to_publish")
	private boolean toPublish;

	@Field("keys")
	private List<DiagnosisKey> keys;

	public UploadEu() {
	}

	public UploadEu(String batchTag, String origin, String country, List<DiagnosisKey> keys) {
		this.batchTag = batchTag;
		this.origin = origin;
		this.country = country;
		this.toPublish = true;
		this.keys = keys;
	}

}


