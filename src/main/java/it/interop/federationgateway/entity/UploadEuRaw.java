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

	@Field("index")
  	private Integer index;
	
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
	private Long ammount;
	
	@Transient
	private Long invalid;

	@CreatedDate
	@Field("created_date")
	private Date createdDate;
	
	public UploadEuRaw() {
	}

	public UploadEuRaw(String batchTag, Integer index, String origin, List<DiagnosisKeyRaw> keys, Long ammount, Long invalid) {
		this(null, batchTag, index, origin, true, true, keys, ammount, invalid, new Date());
	}

	public UploadEuRaw(String id, String batchTag, Integer index, String origin, boolean toProcess, boolean verifiedSign, List<DiagnosisKeyRaw> keys, Long ammount, Long invalid, Date createdDate) {
		this.id = id;
		this.batchTag = batchTag;
		this.index = index;
		this.origin = origin;
		this.toProcess = toProcess;
		this.verifiedSign = verifiedSign;
		this.keys = keys;
		this.ammount = ammount;
		this.invalid = invalid;
		this.createdDate = createdDate;
	}

}


