package it.interop.federationgateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@GetMapping("/liveness")
	public ResponseEntity<String> liveness() {
		return new ResponseEntity<String>("Liveness up!", HttpStatus.OK);
	}

	@GetMapping("/readiness")
	public ResponseEntity<String> readiness() {
		return new ResponseEntity<String>("Readiness up!", HttpStatus.OK);
	}

}
