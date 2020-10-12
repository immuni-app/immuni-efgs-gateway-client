package it.interop.federationgateway.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
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
    @CompoundIndex(name = "efgs_log_country_batch_tag", def = "{'country' : 1, 'batch_tag': 1}")
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
	
	private EfgsLog(OperationType operation, String country, String batchTag, Long amount, Long invalid,
			boolean verifiedSign, Date execution, String executionReport, Audit  audit) {
		this.operation = operation;
		this.country = country;
		this.batchTag = batchTag;
		this.amount = amount;
		this.invalid = invalid;
		this.audit = audit;
		this.verifiedSign = verifiedSign;
		this.execution = execution;
		this.executionReport = executionReport;
	}

	public static EfgsLog buildUploadEfgsLog(String country, String batchTag, Long amount, Long invalid, String batchSignature, String executionReport) {
		Audit  audit = new Audit(country, new Date(), amount,  batchSignature);
	
		return new EfgsLog(EfgsLog.OperationType.UPLOAD, country, batchTag, amount, invalid,
				true, new Date(), executionReport, audit);
	}

	public static EfgsLog buildDownloadEfgsLog(String country, String batchTag, Long amount, Long invalid, 
			boolean verifiedSign, String executionReport, Audit  audit) {
		return new EfgsLog(EfgsLog.OperationType.DOWNLOAD, country, batchTag, amount, invalid,
				verifiedSign, new Date(), executionReport, audit);
	}

	
}
