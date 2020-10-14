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
import java.util.List;

import lombok.Data;

@Data
public class EfgsKey implements Serializable {
	private static final long serialVersionUID = 6174449657018973154L;

  	private byte[] keyData;

	private Integer rollingStartIntervalNumber;

	private Integer rollingPeriod;

	private Integer transmissionRiskLevel;

	private List<String> visitedCountries;

	private ReportType reportType;

	private Integer daysSinceOnsetOfSymptoms;

	private String origin;

	public enum ReportType {
		UNKNOWN,
		CONFIRMED_TEST,
		CONFIRMED_CLINICAL_DIAGNOSIS,
		SELF_REPORT,
		RECURSIVE,
		REVOKED,
	}

	public EfgsKey(byte[] keyData, Integer rollingStartIntervalNumber, Integer rollingPeriod,
			Integer transmissionRiskLevel, List<String> visitedCountries, ReportType reportType,
			Integer daysSinceOnsetOfSymptoms, String origin) {
		this.keyData = keyData;
		this.rollingStartIntervalNumber = rollingStartIntervalNumber;
		this.rollingPeriod = rollingPeriod;
		this.transmissionRiskLevel = transmissionRiskLevel;
		this.visitedCountries = visitedCountries;
		this.reportType = reportType;
		this.daysSinceOnsetOfSymptoms = daysSinceOnsetOfSymptoms;
		this.origin = origin;
	}

}
