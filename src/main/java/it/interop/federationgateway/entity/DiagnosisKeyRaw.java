/*-
 * ---license-start
 * EU-Federation-Gateway-Service / efgs-federation-gateway
 * ---
 * Copyright (C) 2020 T-Systems International GmbH and all other contributors
 * ---
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ---license-end
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
