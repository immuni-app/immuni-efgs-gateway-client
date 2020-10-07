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
	private List<DiagnosisKeyRaw> keys;
	
	
}
