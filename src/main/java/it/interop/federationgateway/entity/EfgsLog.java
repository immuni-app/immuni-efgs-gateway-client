package it.interop.federationgateway.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
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
	private float amount;

	@Field("batch_signature")
	private String batchSignature;

	@Field("verified_sign")
	private boolean verifiedSign;

	@Field("execution")
	private Date execution;

	@Field("execution_report")
  	private String executionReport;

	public enum OperationType {
		UPLOAD,
		DOWNLOAD,
	}

	private EfgsLog(OperationType operation, String country, String batchTag, float amount, String batchSignature,
			boolean verifiedSign, Date execution, String executionReport) {
		this.operation = operation;
		this.country = country;
		this.batchTag = batchTag;
		this.amount = amount;
		this.batchSignature = batchSignature;
		this.verifiedSign = verifiedSign;
		this.execution = execution;
		this.executionReport = executionReport;
	}

	public static EfgsLog buildUploadEfgsLog(String batchTag, float amount, String batchSignature, String executionReport) {
		return new EfgsLog(EfgsLog.OperationType.UPLOAD, "IT", batchTag, amount, batchSignature,
				true, new Date(), executionReport);
	}

	public static EfgsLog buildDownloadEfgsLog(String country, String batchTag, float amount, String batchSignature, 
			boolean verifiedSign, String executionReport) {
		return new EfgsLog(EfgsLog.OperationType.DOWNLOAD, country, batchTag, amount, batchSignature,
				verifiedSign, new Date(), executionReport);
	}

	
}
