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
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import it.interop.federationgateway.model.Audit;
import lombok.Data;

@Data
@CompoundIndexes({
    @CompoundIndex(name = "efgs_log_country_batch_tag", def = "{'country' : 1, 'batch_tag': 1, 'index':1}", unique = false)
})
@Document(collection = "efgs_log")
public class EfgsLog implements Serializable {
	private static final long serialVersionUID = 2330473769691102540L;

	@Id
	private String id;
	
	@Field("operation")
	private OperationType operation;

	@Field("country")
	private String country;

	@Field("batch_tag")
	private String batchTag;

	@Field("index")
	private Integer index;

	@Field("amount")
	private Long amount;

	@Field("invalid")
	private Long invalid;
	
	@Field("ammount_per_country")
	private Map<String, Long> ammountPerCountry;
	
	@Field("verified_sign")
	private boolean verifiedSign;

	@Field("execution")
	private Date execution;

	@Field("execution_report")
  	private String executionReport;

	@Field("audit")
	private Audit  audit;
	
	public enum OperationType {
		UPLOAD,
		DOWNLOAD,
	}

	public EfgsLog() {
	}
	
	private EfgsLog(OperationType operation, String country, String batchTag, Integer index, Long amount, Long invalid,
			boolean verifiedSign, Date execution, String executionReport, Audit  audit) {
		this.operation = operation;
		this.country = country;
		this.batchTag = batchTag;
		this.index = index;
		this.amount = amount;
		this.invalid = invalid;
		this.audit = audit;
		this.verifiedSign = verifiedSign;
		this.execution = execution;
		this.executionReport = executionReport;
	}

	public static EfgsLog buildUploadEfgsLog(String country, String batchTag, Long amount, Long invalid, String batchSignature, String executionReport) {
		Audit  audit = new Audit(country, new Date(), amount,  batchSignature);
	
		return new EfgsLog(EfgsLog.OperationType.UPLOAD, country, batchTag, 1, amount, invalid,
				true, new Date(), executionReport, audit);
	}

	public static EfgsLog buildDownloadEfgsLog(String country, String batchTag, Integer index, Long amount, Long invalid, 
			boolean verifiedSign, String executionReport, Audit  audit) {
		return new EfgsLog(EfgsLog.OperationType.DOWNLOAD, country, batchTag, index, amount, invalid,
				verifiedSign, new Date(), executionReport, audit);
	}

	
}
