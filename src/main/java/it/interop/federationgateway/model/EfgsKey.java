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
