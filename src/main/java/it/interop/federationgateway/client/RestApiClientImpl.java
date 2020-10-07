package it.interop.federationgateway.client;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import it.interop.federationgateway.client.base.RestApiClientBase;
import it.interop.federationgateway.client.base.RestApiException;
import it.interop.federationgateway.client.base.RestApiResponse;
import it.interop.federationgateway.model.Audit;
import it.interop.federationgateway.model.EfgsProto;

@Service
public class RestApiClientImpl extends RestApiClientBase implements RestApiClient {


	@Override
	public RestApiResponse<EfgsProto.DiagnosisKeyBatch> download(String date, String batchTag) throws RestApiException {
		String localVarPath = "/diagnosiskeys/download/{date}".replaceAll("\\{date\\}", date);
		

		StringBuffer url = new StringBuffer(getBaseUrl()).append(localVarPath);
		
		
		HttpHeaders headers = makeBaseHeaders();
		headers.set("Accept", "application/protobuf; version=1.0");
		
		if (batchTag != null) {
			headers.set("batchTag", batchTag);
		}
		
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<byte[]> respEntity = getRestTemplate().exchange(url.toString(), HttpMethod.GET, entity, byte[].class);

		RestApiResponse<EfgsProto.DiagnosisKeyBatch> restApiResponse = null;
		
		if (respEntity != null) {
			try {
				
				EfgsProto.DiagnosisKeyBatch batch = null;
				
				if (respEntity.getStatusCode() == HttpStatus.OK) {
					if (respEntity.getBody() != null) {
						batch = EfgsProto.DiagnosisKeyBatch.newBuilder().mergeFrom(respEntity.getBody()).build();
					}
				}
				
				restApiResponse = new RestApiResponse<EfgsProto.DiagnosisKeyBatch>(respEntity.getStatusCode(), headersToMap(respEntity.getHeaders()), batch);
			} catch (IOException e) {
				throw new RestApiException(e);
			}
		}
		
		return restApiResponse;
	}

	@Override
	public RestApiResponse<List<Audit>> audit(String date, String batchTag) throws RestApiException {
		String localVarPath = "/diagnosiskeys/audit/download/{date}/{batchTag}".replaceAll("\\{date\\}", date).replaceAll("\\{batchTag\\}", batchTag);

		
		StringBuffer url = new StringBuffer(getBaseUrl()).append(localVarPath);
		
		
		HttpHeaders headers = makeBaseHeaders();
		headers.set("Content-Type", "application/json");
		
		
		
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<byte[]> respEntity = getRestTemplate().exchange(url.toString(), HttpMethod.GET, entity, byte[].class);

		RestApiResponse<List<Audit>> restApiResponse = null;

		List<Audit> listAudit = null;
		if (respEntity != null) {
			
			if (respEntity.getStatusCode() == HttpStatus.OK) {
				Gson gson = new Gson();
				Type auditListType = new TypeToken<ArrayList<Audit>>(){}.getType();
				listAudit = gson.fromJson(new String(respEntity.getBody()), auditListType);
			}
			
			restApiResponse = new RestApiResponse<List<Audit>>(respEntity.getStatusCode(), headersToMap(respEntity.getHeaders()), listAudit);
		}
		
		return restApiResponse;
	}

	@Override
	public RestApiResponse<String> upload(String batchTag, String batchSignature, EfgsProto.DiagnosisKeyBatch protoBatch) throws RestApiException {
		String localVarPath = "/diagnosiskeys/upload";
		
		StringBuffer url = new StringBuffer(getBaseUrl()).append(localVarPath);
		
		HttpHeaders headers = makeBaseHeaders();
		headers.set("Content-Type", "application/protobuf; version=1.0");
		headers.set("batchTag", batchTag);
		headers.set("batchSignature", batchSignature);
		
		HttpEntity<byte[]> entity = new HttpEntity<byte[]>(protoBatch.toByteArray(), headers);

		ResponseEntity<String> respEntity = getRestTemplate().exchange(url.toString(), HttpMethod.POST, entity, String.class);
		
		
		RestApiResponse<String> restApiResponse = null;

		if (respEntity != null) {
			String esito = respEntity.getStatusCode().toString();
			if (respEntity.getBody() != null) {
				esito = new String(respEntity.getBody());
			}
			
			restApiResponse = new RestApiResponse<String>(respEntity.getStatusCode(), headersToMap(respEntity.getHeaders()), esito);
		}
		
		return restApiResponse;
	}

}
