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
package it.interop.federationgateway.validator;

import it.interop.federationgateway.entity.DiagnosisKeyRaw;
import it.interop.federationgateway.model.EfgsKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DiagnosisKeyValidator  {
	private static final String VALIDATION_FAILED_MESSAGE = "Validation of diagnosis key failed: ";

	public static boolean isValid(DiagnosisKeyRaw diagnosisKeyRaw) {
		if (diagnosisKeyRaw.getReportType() != EfgsKey.ReportType.CONFIRMED_TEST && diagnosisKeyRaw.getReportType() != EfgsKey.ReportType.CONFIRMED_CLINICAL_DIAGNOSIS) {
			log.error(VALIDATION_FAILED_MESSAGE + "Invalid report-type.");
			return false;
		} else if (diagnosisKeyRaw.getRollingStartIntervalNumber() == 0) {
			log.error(VALIDATION_FAILED_MESSAGE + "Invalid rolling start interval number.");
			return false;
		} else if (diagnosisKeyRaw.getKeyData() == null || diagnosisKeyRaw.getKeyData().isEmpty() || diagnosisKeyRaw.getKeyData().length() < 16) {
			log.error(VALIDATION_FAILED_MESSAGE + "The keydata is empty or null.");
			return false;
		} else if (diagnosisKeyRaw.getRollingPeriod() < 1 || diagnosisKeyRaw.getRollingPeriod() > 144) {
			log.error(VALIDATION_FAILED_MESSAGE + "Invalid rolling period.");
			return false;
		} else if (diagnosisKeyRaw.getTransmissionRiskLevel() <= 0) {
			log.error(VALIDATION_FAILED_MESSAGE + "Invalid transmission risk level.");
			return false;
		}
		return true;
	}
	
}
