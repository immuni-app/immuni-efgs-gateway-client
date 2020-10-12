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
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "upload_eu_raw")
public class UploadEuRaw implements Serializable {

	private static final long serialVersionUID = -9106572163687294395L;

	@Id
	private String id;

	@Field("batch_tag")
  	private String batchTag;

	@Field("origin")
	private String origin;

	@Indexed(name= "upload_eu_raw_toProcess_ix", direction = IndexDirection.ASCENDING, unique = false)
	@Field("to_process")
	private boolean toProcess;

	@Field("verified_sign")
	private boolean verifiedSign;

	@Field("keys")
	private List<DiagnosisKeyRaw> keys;
	
	@Transient
	private Float ammount;
	
	@Transient
	private Float invalid;

	@CreatedDate
	@Field("created_date")
	private Date createdDate;
	
	public UploadEuRaw() {
	}

	public UploadEuRaw(String batchTag, String origin, List<DiagnosisKeyRaw> keys, Float ammount, Float invalid) {
		this(null, batchTag, origin, true, true, keys, ammount, invalid, new Date());
	}

	public UploadEuRaw(String id, String batchTag, String origin, boolean toProcess, boolean verifiedSign, List<DiagnosisKeyRaw> keys, Float ammount, Float invalid, Date createdDate) {
		this.id = id;
		this.batchTag = batchTag;
		this.origin = origin;
		this.toProcess = toProcess;
		this.verifiedSign = verifiedSign;
		this.keys = keys;
		this.ammount = ammount;
		this.invalid = invalid;
		this.createdDate = createdDate;
	}

}


