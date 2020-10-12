package it.interop.federationgateway.batchsigning;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Base64;

import javax.annotation.PostConstruct;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.bouncycastle.cms.CMSException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.interop.federationgateway.client.base.RestApiException;
import lombok.Data;

@Service
public class SignatureGenerator {

	@Value("${signature.external.url}")
	private String externalUrl;

	@Value("${ssldp.jks.path}")
	private String jksPath;
	
	@Value("${ssldp.jks.password}")
	private String jksPassword;

	@Value("${ssldp.cert.password}")
	private String certPassword;

	@Value("${truststore.jks.path}")
	private String jksTrustPath;
	
	@Value("${truststore.jks.password}")
	private String jksTrustPassword;


	private RestTemplate restTemplate;


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
				
				if (outputData != null) {
					return outputData.getSignature();
				} else {
					throw new BatchSignatureException(externalUrl+" return null");
				}

			} else {
				throw new BatchSignatureException(externalUrl+" return status code: "+respEntity.getStatusCode());
			}
		} else {
			throw new BatchSignatureException(externalUrl+" return null");
		}
	}

	@PostConstruct
	private void intRestTemplate() throws RestApiException {
		try {
			KeyStore clientStore = KeyStore.getInstance("JKS");
			clientStore.load(new FileInputStream(jksPath), jksPassword.toCharArray());
			SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
			//sslContextBuilder.useProtocol("TLS");
			//La CA Actalis e certificato
			sslContextBuilder.loadKeyMaterial(clientStore, certPassword.toCharArray());
			
			//Il certificato del gateway
			sslContextBuilder.loadTrustMaterial(new File(jksTrustPath), jksTrustPassword.toCharArray());
			SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build());

		    HttpClientBuilder clientBuilder = HttpClientBuilder.create();

		    clientBuilder.disableCookieManagement();

		    CloseableHttpClient httpClient = clientBuilder.setSSLSocketFactory(sslConnectionSocketFactory).build();
			
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
			requestFactory.setConnectTimeout(10000); // 10 seconds
			requestFactory.setReadTimeout(10000); // 10 seconds
			
			restTemplate = new RestTemplate(requestFactory);
			
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | KeyManagementException | IOException e) {
			throw new RestApiException(e);
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


