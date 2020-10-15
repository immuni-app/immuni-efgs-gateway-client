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

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "batch_file")
public class BatchFile {

	@Id
	private String id;

	@Field("index")
	private int index;
	
	@Field("origin")
	private String origin;

	@Field("batch_tag")
  	private String batchTag;

	@Field("upload_eu_report")
  	private String uploadEuReport;

	@Field("keys")
	private List<DiagnosisKey> keys;
	
	public BatchFile() {
	}

	public BatchFile(String id, int index, String origin, String batchTag, String uploadEuReport,
			List<DiagnosisKey> keys) {
		super();
		this.id = id;
		this.index = index;
		this.origin = origin;
		this.batchTag = batchTag;
		this.uploadEuReport = uploadEuReport;
		this.keys = keys;
	}


	
}
