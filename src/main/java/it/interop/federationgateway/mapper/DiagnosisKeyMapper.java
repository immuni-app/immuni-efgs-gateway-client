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
package it.interop.federationgateway.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;

import com.google.protobuf.ByteString;

import it.interop.federationgateway.entity.BatchFile;
import it.interop.federationgateway.entity.DiagnosisKey;
import it.interop.federationgateway.entity.DiagnosisKeyRaw;
import it.interop.federationgateway.entity.UploadEu;
import it.interop.federationgateway.entity.UploadEuRaw;
import it.interop.federationgateway.model.EfgsKey;
import it.interop.federationgateway.model.EfgsProto;
import it.interop.federationgateway.validator.DiagnosisKeyValidator;
import lombok.Setter;

public class DiagnosisKeyMapper {

	@Setter
	public static String localCountry;
	
	private static Integer HIGHEST_TRASMSSION_RISK_LEVEL = 3;
	private static Integer DEFAULT_DAYS_SINCE_ONSET_OF_SYMPTOMS = 4000;
	private static EfgsKey.ReportType DEFAULT_REPORT_TYPE = EfgsKey.ReportType.CONFIRMED_TEST;

	public static List<EfgsKey> protoToEfgsKey(List<EfgsProto.DiagnosisKey> proto) {
		return proto.stream().map(p -> protoToEfgsKey(p)).collect(Collectors.toList());
	}

	private static EfgsKey protoToEfgsKey(EfgsProto.DiagnosisKey proto) {
		EfgsKey entity = new EfgsKey(byteStringToByteArray(proto.getKeyData()), proto.getRollingStartIntervalNumber(),
				proto.getRollingPeriod(), proto.getTransmissionRiskLevel(), proto.getVisitedCountriesList(),
				mapReportType(proto.getReportType()), proto.getDaysSinceOnsetOfSymptoms(), proto.getOrigin());
		return entity;
	}

	public static ArrayList<EfgsProto.DiagnosisKey> efgsKeyToProto(List<EfgsKey> entity) {
		ArrayList<EfgsProto.DiagnosisKey> efgsProtoDiagnosisKeys = new ArrayList<EfgsProto.DiagnosisKey>();
		for (EfgsKey ent : entity) {
			efgsProtoDiagnosisKeys.add(efgsKeyToProto(ent));
		}
		return efgsProtoDiagnosisKeys;
	}

	public static EfgsProto.DiagnosisKey efgsKeyToProto(EfgsKey keyPayload) {
		return EfgsProto.DiagnosisKey.newBuilder().setKeyData(byteArrayToByteString(keyPayload.getKeyData()))
				.setRollingStartIntervalNumber(keyPayload.getRollingStartIntervalNumber())
				.setRollingPeriod(keyPayload.getRollingPeriod())
				.setTransmissionRiskLevel(keyPayload.getTransmissionRiskLevel())
				.addAllVisitedCountries(keyPayload.getVisitedCountries())
				.setOrigin(keyPayload.getOrigin())
				.setReportType(mapReportType(keyPayload.getReportType()))
				.setDaysSinceOnsetOfSymptoms(keyPayload.getDaysSinceOnsetOfSymptoms()).build();
	}

	public static UploadEuRaw efgsKeysToEntity(List<EfgsKey> efgsKeys, String batchTag) throws Exception {
		UploadEuRaw diagnosisKeyEntity = null;
		if (efgsKeys != null && efgsKeys.size() > 0) {
			List<DiagnosisKeyRaw> keysPayload = new ArrayList<DiagnosisKeyRaw>();
			String origin = efgsKeys.get(0).getOrigin();

			Integer invalid = 0;
			for (EfgsKey efgsKey : efgsKeys) {
				DiagnosisKeyRaw diagnosisKeyPayload = efgsKeysToEntityPayload(efgsKey);
				boolean valid = DiagnosisKeyValidator.isValid(efgsKey);
				diagnosisKeyPayload.setValid(valid);
				keysPayload.add(diagnosisKeyPayload);
				invalid += valid ? 1 : 0;
			}
			diagnosisKeyEntity = new UploadEuRaw(batchTag, origin, keysPayload, Long.valueOf(keysPayload.size()), Long.valueOf(invalid));
		}
		return diagnosisKeyEntity;
	}

	private static DiagnosisKeyRaw efgsKeysToEntityPayload(EfgsKey efgsKey) {
		return new DiagnosisKeyRaw(byteToBase64(efgsKey.getKeyData()), efgsKey.getRollingStartIntervalNumber(),
				efgsKey.getRollingPeriod(), efgsKey.getTransmissionRiskLevel(), efgsKey.getVisitedCountries(),
				efgsKey.getReportType(), efgsKey.getDaysSinceOnsetOfSymptoms());
	}

	public static Map<String, List<EfgsKey>> protoToEfgsKeyPerOrigin(List<EfgsProto.DiagnosisKey> proto) {
		List<EfgsKey> efgsKeys = protoToEfgsKey(proto);
		return splitKeysPerOrigin(efgsKeys);
	}

	public static List<EfgsKey> entityToEfgsKeys(BatchFile diagnosisKeyEntity) {
		List<EfgsKey> entityKeys = new ArrayList<EfgsKey>();
		for (DiagnosisKey diagnosisKeyPayload : diagnosisKeyEntity.getKeys()) {
			entityKeys.add(entityPayloadToEfgsKeys(diagnosisKeyPayload, diagnosisKeyEntity.getOrigin()));
		}
		return entityKeys;
	}

	private static EfgsKey entityPayloadToEfgsKeys(DiagnosisKey diagnosisKeyPayload, String origin) {
		return new EfgsKey(base64ToByte(diagnosisKeyPayload.getKeyData()),
				diagnosisKeyPayload.getRollingStartIntervalNumber(), diagnosisKeyPayload.getRollingPeriod(),
				HIGHEST_TRASMSSION_RISK_LEVEL, diagnosisKeyPayload.getCountriesOfInterest(),
				DEFAULT_REPORT_TYPE, calculateDsos(diagnosisKeyPayload), origin);
	}

	public static Map<String, List<EfgsKey>> splitKeysPerOrigin(List<EfgsKey> efgsKeys) {
		Map<String, List<EfgsKey>> entitiesMap = new HashMap<String, List<EfgsKey>>();

		for (EfgsKey efgsKey : efgsKeys) {
			if (!entitiesMap.containsKey(efgsKey.getOrigin())) {
				entitiesMap.put(efgsKey.getOrigin(), new ArrayList<EfgsKey>());
			}
			entitiesMap.get(efgsKey.getOrigin()).add(efgsKey);
		}

		return entitiesMap;
	}

	public static Map<String, UploadEu> splitKeysPerVisitatedCountry(UploadEuRaw uploadEuRaw) {
		Map<String, UploadEu> keyCountryMap = new HashMap<String, UploadEu>();

		if (uploadEuRaw != null && uploadEuRaw.getKeys() != null) {

			for (DiagnosisKeyRaw key : uploadEuRaw.getKeys()) {

				if (key.isValid()) {

					if (key.getVisitedCountries().contains(localCountry)) {
						//Se la chiave ha IT nell'elenco dei paesi visitati: la chiave è considerata come locale e verrà distribuita a tutti gli utenti IT
						if (!keyCountryMap.containsKey(localCountry)) {
							keyCountryMap.put(localCountry, new UploadEu(uploadEuRaw.getBatchTag(),
									uploadEuRaw.getOrigin(), localCountry, new ArrayList<DiagnosisKey>()));
						}
						keyCountryMap.get(localCountry).getKeys().add(new DiagnosisKey(key.getKeyData(),
								key.getRollingStartIntervalNumber(), key.getRollingPeriod()));

					} else {
						if (!keyCountryMap.containsKey(uploadEuRaw.getOrigin())) {
							keyCountryMap.put(uploadEuRaw.getOrigin(), new UploadEu(uploadEuRaw.getBatchTag(),
									uploadEuRaw.getOrigin(), uploadEuRaw.getOrigin(), new ArrayList<DiagnosisKey>()));
						}
						keyCountryMap.get(uploadEuRaw.getOrigin()).getKeys().add(new DiagnosisKey(key.getKeyData(),
								key.getRollingStartIntervalNumber(), key.getRollingPeriod()));

						if (key.getVisitedCountries() != null) {
							for (String country : key.getVisitedCountries()) {
								if (!StringUtils.isEmpty(country)) {
									if (!keyCountryMap.containsKey(country)) {
										keyCountryMap.put(country, new UploadEu(uploadEuRaw.getBatchTag(),
												uploadEuRaw.getOrigin(), country, new ArrayList<DiagnosisKey>()));
									}
									keyCountryMap.get(country).getKeys().add(new DiagnosisKey(key.getKeyData(),
											key.getRollingStartIntervalNumber(), key.getRollingPeriod()));
								}
							}
						}
					}
				}
			}

		}
		return keyCountryMap;
	}

	private static Integer calculateDsos(DiagnosisKey diagnosisKeyPayload) {
		Long time = Long.valueOf(diagnosisKeyPayload.getRollingStartIntervalNumber()) * 600 * 1000; 
		Long days = (new Date().getTime() - time) / 86400000;
		return DEFAULT_DAYS_SINCE_ONSET_OF_SYMPTOMS - days.intValue();
	}
	
	public static byte[] byteStringToByteArray(ByteString byteString) {
		return byteString.toByteArray();
	}

	public static ByteString byteArrayToByteString(byte[] byteArray) {
		return ByteString.copyFrom(byteArray);
	}

	public static byte[] base64ToByte(String base64) {
		Base64 base64Url = new Base64();
		return base64Url.decode(base64);
	}

	public static String byteToBase64(byte[] bytes) {
		Base64 base64Url = new Base64();
		return base64Url.encodeAsString(bytes);
	}
	
	public static EfgsProto.ReportType mapReportType(EfgsKey.ReportType verificationType) {
		switch (verificationType) {
		case UNKNOWN:
			return EfgsProto.ReportType.UNKNOWN;
		case CONFIRMED_TEST:
			return EfgsProto.ReportType.CONFIRMED_TEST;
		case CONFIRMED_CLINICAL_DIAGNOSIS:
			return EfgsProto.ReportType.CONFIRMED_CLINICAL_DIAGNOSIS;
		case SELF_REPORT:
			return EfgsProto.ReportType.SELF_REPORT;
		case RECURSIVE:
			return EfgsProto.ReportType.RECURSIVE;
		case REVOKED:
			return EfgsProto.ReportType.REVOKED;
		default:
			return null;
		}
	}

	public static EfgsKey.ReportType mapReportType(EfgsProto.ReportType reportType) {
		switch (reportType) {
		case UNKNOWN:
			return EfgsKey.ReportType.UNKNOWN;
		case CONFIRMED_TEST:
			return EfgsKey.ReportType.CONFIRMED_TEST;
		case CONFIRMED_CLINICAL_DIAGNOSIS:
			return EfgsKey.ReportType.CONFIRMED_CLINICAL_DIAGNOSIS;
		case SELF_REPORT:
			return EfgsKey.ReportType.SELF_REPORT;
		case RECURSIVE:
			return EfgsKey.ReportType.RECURSIVE;
		case REVOKED:
			return EfgsKey.ReportType.REVOKED;
		default:
			return null;
		}
	}

}
