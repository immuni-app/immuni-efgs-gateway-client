package it.interop.federationgateway.client.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
//import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import it.interop.federationgateway.utils.Utility;
import lombok.Getter;

public class RestApiClientBase {

	@Getter
	@Value("${efgs.base_url}")
	private String baseUrl;

	@Value("${efgs.user_agent}")
	private String userAgent;

	@Value("${security.jks.path}")
	private String jksPath;
	
	@Value("${security.jks.password}")
	private String jksPassword;
	
	@Value("${security.cert.password}")
	private String certPassword;
	
	@Value("${proxy.host}")
	private String proxyHost;

	@Value("${proxy.port}")
	private String proxyPort;
	
	@Value("${proxy.user}")
	private String proxyUser;

	@Value("${proxy.password}")
	private String proxyPassword;

	@Getter
	@Autowired
	private RestTemplate restTemplate;

	
	@Bean
	private RestTemplate initRestTemplate() throws RestApiException {
		try {
			KeyStore clientStore = KeyStore.getInstance("PKCS12");
			clientStore.load(new FileInputStream(jksPath), jksPassword.toCharArray());
			SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
			//sslContextBuilder.useProtocol("TLS");
			sslContextBuilder.loadKeyMaterial(clientStore, certPassword.toCharArray());
			sslContextBuilder.loadTrustMaterial(new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build());

		    HttpClientBuilder clientBuilder = HttpClientBuilder.create();

			if (!Utility.isEmpty(proxyHost) && !Utility.isEmpty(proxyPort)) {
			    HttpHost myProxy = new HttpHost(proxyHost, Integer.parseInt(proxyPort));
	
			    clientBuilder.setProxy(myProxy);
			    if (!Utility.isEmpty(proxyUser) && !Utility.isEmpty(proxyPassword)) {
				    CredentialsProvider credsProvider = new BasicCredentialsProvider();
				    credsProvider.setCredentials( 
				        new AuthScope(proxyHost, Integer.parseInt(proxyPort)), 
				        new UsernamePasswordCredentials(proxyUser, proxyPassword)
				    );
				    clientBuilder.setDefaultCredentialsProvider(credsProvider);
			    }
			}
		    clientBuilder.disableCookieManagement();

		    CloseableHttpClient httpClient = clientBuilder.setSSLSocketFactory(sslConnectionSocketFactory).build();
			
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
			requestFactory.setConnectTimeout(10000); // 10 seconds
			requestFactory.setReadTimeout(10000); // 10 seconds
			
			return new RestTemplate(requestFactory);
			
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | KeyManagementException | IOException e) {
			throw new RestApiException(e);
		}
	}

	protected Map<String, List<String>> headersToMap(HttpHeaders headers) {
		Map<String, List<String>> headersList = null;
		
		if (headers != null) {
			 headersList = new HashMap<String, List<String>>();
			Set<String> names = headers.keySet();
			
			for (String name: names) {
				headersList.put(name, headers.get(name));
			}
		}
		
		return headersList;
	}
	
	protected HttpHeaders makeBaseHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("User-Agent", userAgent);
		
		return headers;
	}
	
	
}
