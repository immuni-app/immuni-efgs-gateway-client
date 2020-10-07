package it.interop.federationgateway.client.base;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import it.interop.federationgateway.client.RestApiClient;
import lombok.Data;

@Data
public class RestApiResponse <T> {
    final private HttpStatus statusCode;
    final private Map<String, List<String>> headers;
    final private T data;

    public RestApiResponse(HttpStatus statusCode, Map<String, List<String>> headers) {
        this(statusCode, headers, null);
    }

    public RestApiResponse(HttpStatus statusCode, Map<String, List<String>> headers, T data) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.data = data;
    }

    
    public String getBatchTag() {
    	List<String> header = getHeaders().get(RestApiClient.BATCH_TAG);
    	return (header!=null && header.size()>0) ? header.get(0) : null;
    }
    
    public String getNextBatchTag() {
    	List<String> header = getHeaders().get(RestApiClient.NEXT_BATCH_TAG);
    	return (header!=null && header.size()>0) ? header.get(0) : null;
    }


}
