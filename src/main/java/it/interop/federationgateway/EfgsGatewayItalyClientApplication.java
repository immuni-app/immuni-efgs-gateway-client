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
package it.interop.federationgateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import it.interop.federationgateway.mapper.DiagnosisKeyMapper;
import it.interop.federationgateway.validator.DiagnosisKeyValidator;

@SpringBootApplication
@EnableScheduling
public class EfgsGatewayItalyClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(EfgsGatewayItalyClientApplication.class, args);
	}
	
	@Value("${efgs.origin_country}")
	private void setLocalCountry(String country) {
		DiagnosisKeyMapper.setLocalCountry(country);
	}

	@Value("${efgs.data_retention_days}")
	private void setDataRetentionDays(String dataRetentionDays) {
		Integer DATA_RETENTION_DAYS = Integer.parseInt(dataRetentionDays);
		DiagnosisKeyValidator.setDATA_RETENTION_DAYS(DATA_RETENTION_DAYS);
	}
	
}
