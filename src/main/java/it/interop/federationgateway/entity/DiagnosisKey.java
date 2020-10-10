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

import lombok.Data;

@Data
public class DiagnosisKey implements Serializable {
	private static final long serialVersionUID = -424819275022738583L;

	@Field("key_data")
  	protected String keyData;

	@Field("rolling_start_number")
	protected Integer rollingStartIntervalNumber;

	@Field("rolling_period")
	protected Integer rollingPeriod;

	@Field("countries_of_interest")
	private List<String> countriesOfInterest;

	public DiagnosisKey(String keyData, Integer rollingStartIntervalNumber, Integer rollingPeriod, List<String> countriesOfInterest) {
		this.keyData = keyData;
		this.rollingStartIntervalNumber = rollingStartIntervalNumber;
		this.rollingPeriod = rollingPeriod;
		this.countriesOfInterest = countriesOfInterest;
	}
	
	public DiagnosisKey(String keyData, Integer rollingStartIntervalNumber, Integer rollingPeriod) {
		this.keyData = keyData;
		this.rollingStartIntervalNumber = rollingStartIntervalNumber;
		this.rollingPeriod = rollingPeriod;
		this.countriesOfInterest = null;
	}


}
