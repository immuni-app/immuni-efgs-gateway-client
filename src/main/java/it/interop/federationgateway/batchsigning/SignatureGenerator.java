package it.interop.federationgateway.batchsigning;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

import javax.annotation.PostConstruct;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.interop.federationgateway.utils.CertUtils;
import lombok.Data;

@Service
public class SignatureGenerator {

	/*Internal signature*/
	private SignerInfoGenerator signerInfo;
	private X509CertificateHolder certificateHolder;

	/*External signature*/
	private RestTemplate restTemplate;

	@Value("${batch.signature.internal_external}")
	private String internalOrExternal;
	
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

	private boolean isInternal() {
		return "internal".equalsIgnoreCase(internalOrExternal);
	}
	
	@PostConstruct
	private void initSignatureGenerator() throws BatchSignatureException {
		if (isInternal()) {
			initInternalSignatureGenerator();
		} else {
			initExternalSignatureGenerator();
		}
	}
  
	private void initInternalSignatureGenerator() throws BatchSignatureException {
		try {
			X509Certificate certificate = CertUtils.loadCertificateFromFile(internalFileName);
			PrivateKey privateKey = CertUtils.loadPrivateKeyFromFile(internalFileName);
			DigestCalculatorProvider digestCalculatorProvider = new JcaDigestCalculatorProviderBuilder().build();
			ContentSigner contentSigner = new JcaContentSignerBuilder(certificate.getSigAlgName()).build(privateKey);
			signerInfo = new JcaSignerInfoGeneratorBuilder(digestCalculatorProvider).build(contentSigner, certificate);
			certificateHolder = new X509CertificateHolder(certificate.getEncoded());
		} catch (CertificateException | IOException | OperatorCreationException e) {
			throw new BatchSignatureException(e);
		}
	}
	
	private void initExternalSignatureGenerator() throws BatchSignatureException {
		try {
			KeyStore clientStore = KeyStore.getInstance("PKCS12");
			clientStore.load(new FileInputStream(externaJksPath), externaJksPassword.toCharArray());
			SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
			//sslContextBuilder.useProtocol("TLS");
			sslContextBuilder.loadKeyMaterial(clientStore, externaCertPassword.toCharArray());
			sslContextBuilder.loadTrustMaterial(new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build());

		    HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		    clientBuilder.disableCookieManagement();

		    CloseableHttpClient httpClient = clientBuilder.setSSLSocketFactory(sslConnectionSocketFactory).build();
			
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
			requestFactory.setConnectTimeout(10000); // 10 seconds
			requestFactory.setReadTimeout(10000); // 10 seconds
			
			restTemplate = new RestTemplate(requestFactory);
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException | UnrecoverableKeyException | KeyManagementException e) {
			throw new BatchSignatureException(e);
		}

	}
	
	public String getSignatureForBytes(final byte[] data) throws BatchSignatureException {
		try {
			if (isInternal()) {
				return getInternalSignatureForBytes(data);
			} else {
				return getExternalSignatureForBytes(data);
			}
		} catch(CMSException | IOException e) {
			throw new BatchSignatureException(e);
		}
	}
	
	private String getInternalSignatureForBytes(final byte[] data) throws CMSException, IOException {
		final CMSSignedDataGenerator signedDataGenerator = new CMSSignedDataGenerator();
		signedDataGenerator.addSignerInfoGenerator(signerInfo);
		signedDataGenerator.addCertificate(certificateHolder);
		
		CMSSignedData signedData = signedDataGenerator.generate(new CMSProcessableByteArray(data), false);
		return Base64.getEncoder().encodeToString(signedData.getEncoded());
	}
	
	private String getExternalSignatureForBytes(final byte[] data) throws CMSException, IOException, BatchSignatureException {
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


