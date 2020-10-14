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
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

import it.interop.federationgateway.model.EfgsKey.ReportType;
import lombok.Data;

@Data
public class DiagnosisKeyRaw implements Serializable {
	private static final long serialVersionUID = -6602522114553838580L;

	@Field("key_data")
  	private String keyData;

	@Field("rolling_start_number")
	private Integer rollingStartIntervalNumber;

	@Field("rolling_period")
	private Integer rollingPeriod;

	@Field("transmission_risk_level")
	private Integer transmissionRiskLevel;

	@Field("visited_countries")
	private List<String> visitedCountries;

	@Field("report_type")
	private ReportType reportType;

	@Field("days_since_on_set_of_symptoms")
	private Integer daysSinceOnsetOfSymptoms;
	
	@Field("valid")
	private boolean valid;
	

	public DiagnosisKeyRaw(String keyData, Integer rollingStartIntervalNumber, Integer rollingPeriod,
			Integer transmissionRiskLevel, List<String> visitedCountries, ReportType reportType,
			Integer daysSinceOnsetOfSymptoms) {
		this.keyData = keyData;
		this.rollingStartIntervalNumber = rollingStartIntervalNumber;
		this.rollingPeriod = rollingPeriod;
		this.transmissionRiskLevel = transmissionRiskLevel;
		this.visitedCountries = visitedCountries;
		this.reportType = reportType;
		this.daysSinceOnsetOfSymptoms = daysSinceOnsetOfSymptoms;
	}



}
