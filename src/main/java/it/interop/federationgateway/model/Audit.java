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
