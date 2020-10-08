package it.interop.federationgateway.batchsigning;

import java.io.IOException;
import java.util.Base64;

import org.bouncycastle.cms.CMSException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.Data;

@Service
public class SignatureGenerator {

	/*External signature*/
	private RestTemplate restTemplate;

	@Value("${batch.signature.internal.path}")
	private String internalFileName;

	@Value("${batch.signature.external.url}")
	private String externalUrl;

	@Value("${batch.signature.external.jks.path}")
	private String externaJksPath;

	@Value("${batch.signature.external.jks.password}")
	private String externaJksPassword;

	@Value("${batch.signature.external.cert.password}")
	private String externaCertPassword;

	
	public String getSignatureForBytes(final byte[] data) throws CMSException, IOException, BatchSignatureException {
		HttpHeaders headers = new HttpHeaders();

		headers.set("User-Agent", "");
		headers.set("Content-Type", "application/json");
		
		InputData inputData = new InputData(Base64.getEncoder().encodeToString(data));
		
		HttpEntity<InputData> entity = new HttpEntity<InputData>(inputData, headers);

		ResponseEntity<OutputData> respEntity = restTemplate.exchange(externalUrl, HttpMethod.POST, entity, OutputData.class);
		
		if (respEntity != null) {
			if (respEntity.getStatusCode() == HttpStatus.OK) {
				OutputData outputData = respEntity.getBody();
				return outputData != null ? outputData.getSignature() : null;
			} else {
				throw new BatchSignatureException(externalUrl+" return status code: "+respEntity.getStatusCode());
			}
		} else {
			throw new BatchSignatureException(externalUrl+" return null");
		}
	}
	
}

@Data
class InputData {
	private String prehashed;
	private String input;

	public InputData(String input) {
		this.prehashed = "false";
		this.input = input;
	}
}

@Data
class OutputData {
	private String signature;

}


