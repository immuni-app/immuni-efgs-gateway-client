package it.interop.federationgateway.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "efgs_worker_info")
public class EfgsWorkerInfo implements Serializable {

	private static final long serialVersionUID = 9143326595492707765L;
	private static SimpleDateFormat batchDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat batchTagBaseFormat = new SimpleDateFormat("yyyyMMdd");

	@Id
	private String id;
	
	@Field("operation")
	private OperationType operation;

	@Field("batch_date")
	private String batchDate;

	@Field("batch_tag_base")
	private String batchTagBase;

	@Field("batch_tag_index")
	private Integer batchTagIndex;

	@Field("last_execution")
	private Date lastExecution;
	
	
	public enum OperationType {
		UPLOAD,
		DOWNLOAD,
	}

	public EfgsWorkerInfo() {
		
	}

	public EfgsWorkerInfo(OperationType operation) {
		this.operation = operation;
		this.batchDate = batchDateFormat.format(new Date());
		this.lastExecution = new Date();
		if (operation == OperationType.UPLOAD) {
			this.batchTagBase = batchTagBaseFormat.format(new Date());
			this.batchTagIndex = 0;
		}
	}

	public void setBatchDate(String date) {
		this.batchDate = date;
	}

	public void setBatchTag(String batchTag) {
		String[] splitted = batchTag.split("-");
		this.batchTagBase = splitted[0];
		this.batchTagIndex = Integer.parseInt(splitted[1]);
	}
	
	public String getBatchTag() {
		String batchTag = null;
		if (batchTagBase != null && batchTagIndex != null) {
			batchTag = new StringBuffer()
				.append(batchTagBase)
				.append("-")
				.append(batchTagIndex)
				.toString();
		}
		return batchTag;
	}

	public String getNextBatchTag() {
		String batchTag = null;
		if (batchTagBase != null && batchTagIndex != null) {
			batchTag = new StringBuffer()
				.append(batchTagBase)
				.append("-")
				.append(batchTagIndex+1)
				.toString();
		}
		return batchTag;
	}


	public static String getToDayBatchDate() {
		return batchDateFormat.format(new Date());
	}

	public static String getDefaultBatchTag() {
		return new StringBuffer(batchTagBaseFormat.format(new Date()))
				.append("-0").toString();
	}

	public String getNextBatchDate() {
		try {
			Calendar c = Calendar.getInstance(); 
			c.setTime(batchDateFormat.parse(batchDate));
			c.add(Calendar.DATE, 1);
			return batchDateFormat.format(c.getTime());
		} catch (ParseException e) {
			return null;
		} 
	}

	public boolean isBatchDateBeforeToDay() {
		try {
			Calendar c = Calendar.getInstance(); 
			c.setTime(batchDateFormat.parse(batchDate));
			Date batchDateD = c.getTime();
	
			c.setTime(batchDateFormat.parse(getToDayBatchDate()));
			Date todayD = c.getTime();
			
			return batchDateD.before(todayD);
		} catch (ParseException e) {
			return false;
		} 
	}

}
