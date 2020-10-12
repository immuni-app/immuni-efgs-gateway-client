package it.interop.federationgateway.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Audit implements Serializable {
	private static final long serialVersionUID = -5069959658520025863L;

	private String country;
	private Date  uploadedTime;
	private String uploaderThumbprint;
	private Float amount;
	private String batchSignature;
	
	
}
