/*-
 * ---license-start
 * EU-Federation-Gateway-Service / efgs-federation-gateway
 * ---
 * Copyright (C) 2020 T-Systems International GmbH and all other contributors
 * ---
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ---license-end
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

	public UploadEu(String batchTag, String origin, String country, List<DiagnosisKey> keys) {
		this.batchTag = batchTag;
		this.origin = origin;
		this.country = country;
		this.toPublish = true;
		this.keys = keys;
	}

}


