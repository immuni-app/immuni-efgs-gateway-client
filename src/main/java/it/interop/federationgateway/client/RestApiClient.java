package it.interop.federationgateway.client;

import java.util.List;

import it.interop.federationgateway.client.base.RestApiException;
import it.interop.federationgateway.client.base.RestApiResponse;
import it.interop.federationgateway.model.Audit;
import it.interop.federationgateway.model.EfgsProto;

public interface RestApiClient {
	public static String NEXT_BATCH_TAG = "nextBatchTag";
	public static String BATCH_TAG = "batchTag";

	public RestApiResponse<EfgsProto.DiagnosisKeyBatch> download(String date, String batchTag) throws RestApiException;
	public RestApiResponse<String> upload(String batchTag, String batchSignature, EfgsProto.DiagnosisKeyBatch protoBatch) throws RestApiException;
	public RestApiResponse<List<Audit>> audit(String date, String batchTag) throws RestApiException;

}
