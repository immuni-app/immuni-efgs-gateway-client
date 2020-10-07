package it.interop.federationgateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import it.interop.federationgateway.mapper.DiagnosisKeyMapper;

@SpringBootApplication
@EnableScheduling
public class EfgsGatewayItalyClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(EfgsGatewayItalyClientApplication.class, args);
	}
	
	@Value("${efgs.origin_country}")
	private void setLocalCountry(String country) {
		DiagnosisKeyMapper.setLocalCountry(country);
	}

}
