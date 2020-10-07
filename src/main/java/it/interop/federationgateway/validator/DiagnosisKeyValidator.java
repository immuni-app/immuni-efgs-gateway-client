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
