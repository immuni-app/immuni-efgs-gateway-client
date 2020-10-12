package it.interop.federationgateway.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Audit implements Serializable {
	private static final long serialVersionUID = -5069959658520025863L;

	private String country;
	private Date  uploadedTime;
	private String uploaderCertificate;
	private String uploaderThumbprint;
	private String uploaderOperatorSignature;
	private String signingCertificate;
	private String uploaderSigningThumbprint;
	private String signingCertificateOperatorSignature;
	private Long amount;
	private String batchSignature;

	
	public Audit() {
	}

	public Audit(String country, Date uploadedTime, Long amount, String batchSignature) {
		this.country = country;
		this.uploadedTime = uploadedTime;
		this.amount = amount;
		this.batchSignature = batchSignature;
	}
	
}
