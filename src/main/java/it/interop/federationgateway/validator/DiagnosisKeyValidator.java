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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import it.interop.federationgateway.entity.DiagnosisKey;
import it.interop.federationgateway.model.EfgsKey;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
/*
 * keyData	
 * Used to generate broadcasts that are collected on the other devices. 
 * These connect to provide a record of the interaction between two devices. 
 * This information remains on a device until and unless the user tests positive, 
 * at which point the user can choose to share that information with the internet-accessible server.
 */

/*
 * rollingStartNumber	
 * The time at which the key was generated, in 10-minute intervals since UTC epoch. 
 * This time will align to UTC midnight.
 */

/*
 * transmissionRiskLevel
 * 0: Unused
 * 1: Confirmed test - Low transmission risk level
 * 2: Confirmed test - Standard transmission risk level
 * 3: Confirmed test - High transmission risk level
 * 4: Confirmed clinical diagnosis
 * 5: Self report
 * 6: Negative case
 * 7: Recursive case
 * 8: Unused/custom
 */

/*
 * reportType
 * 0: UNKNOWN. Report type metadata is missing from the diagnosis key. In v1.7 and higher, keys with report type set to UNKNOWN do not contribute to the risk value.
 * 1: CONFIRMED_TEST. A medical provider has confirmed the user had a positive diagnostic test for COVID-19.
 * 2: CONFIRMED_CLINICAL_DIAGNOSIS. A medical provider has confirmed the user had symptoms consistent with a COVID-19 diagnosis.
 * 3: SELF_REPORT. The user has self-reported symptoms consistent with COVID-19 without confirmation from a medical provider.
 * 4: RECURSIVE. This value is reserved for future use.
 * 5: REVOKED. In v1.5, REVOKED is not used. In v1.6 and higher, REVOKED eliminates exposures associated with that key from the detected exposures.
 */



@Slf4j
public class DiagnosisKeyValidator {
	private static final String VALIDATION_FAILED_MESSAGE = "Validation of diagnosis key failed: ";

	private static Integer KEY_DATA_LENGTH = 16; 
	private static Integer MAX_ROLLING_START_PERIOD = 144; 
	private static Integer MAX_TRASMISSION_RISK_LEVEL = 8; 
	@Setter
	public static Integer DATA_RETENTION_DAYS = 15;

	public static boolean isValid(EfgsKey efgsKey) {
		if (efgsKey.getReportType() != EfgsKey.ReportType.CONFIRMED_TEST
				&& efgsKey.getReportType() != EfgsKey.ReportType.CONFIRMED_CLINICAL_DIAGNOSIS) {
			log.error(VALIDATION_FAILED_MESSAGE + "Invalid report-type.");
			return false;
		} else if (efgsKey.getRollingStartIntervalNumber() < minRollingStartIntervalNumber() 
				|| efgsKey.getRollingStartIntervalNumber() > maxRollingStartIntervalNumber()) {
			log.error(VALIDATION_FAILED_MESSAGE + "Invalid rolling start interval number.");
			return false;
		} else if (efgsKey.getKeyData() == null || efgsKey.getKeyData().length < KEY_DATA_LENGTH) {
			log.error(VALIDATION_FAILED_MESSAGE + "The keydata is empty or null.");
			return false;
		} else if (efgsKey.getRollingPeriod() < 1 || efgsKey.getRollingPeriod() > MAX_ROLLING_START_PERIOD) {
			log.error(VALIDATION_FAILED_MESSAGE + "Invalid rolling period.");
			return false;
		} else if (efgsKey.getTransmissionRiskLevel() < 0 || efgsKey.getTransmissionRiskLevel() > MAX_TRASMISSION_RISK_LEVEL) {
			log.error(VALIDATION_FAILED_MESSAGE + "Invalid transmission risk level.");
			return false;
		}
		return true;
	}

	public static boolean isValid(DiagnosisKey diagnosisKey) {
		if (diagnosisKey.getRollingStartIntervalNumber() < minRollingStartIntervalNumber() 
				|| diagnosisKey.getRollingStartIntervalNumber() > maxRollingStartIntervalNumber()) {
			log.error(VALIDATION_FAILED_MESSAGE + "Invalid rolling start interval number {} for key {}.", diagnosisKey.getRollingStartIntervalNumber(), diagnosisKey.getKeyData());
			return false;
		}
		return true;
	}

	private static Long minRollingStartIntervalNumber() {
    	Calendar calendar = new GregorianCalendar();
    	calendar.setTime(new Date());
	    calendar.add(Calendar.DAY_OF_MONTH, -DATA_RETENTION_DAYS);
		return calendar.getTimeInMillis() / 1000 / 600;
	}
	
	private static Long maxRollingStartIntervalNumber() {
    	Calendar calendar = new GregorianCalendar();
    	calendar.setTime(new Date());
		return calendar.getTimeInMillis() / 1000 / 600;
	}
	
}
