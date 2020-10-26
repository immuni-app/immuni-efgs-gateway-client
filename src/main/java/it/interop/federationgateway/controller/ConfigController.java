package it.interop.federationgateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.interop.federationgateway.entity.EfgsWorkerInfo;
import it.interop.federationgateway.model.EfgsConfig;
import it.interop.federationgateway.repository.EfgsWorkerInfoRepository;

@RestController
@RequestMapping("/config")
public class ConfigController {
	
	@Autowired(required=true)
	private EfgsWorkerInfoRepository efgsWorkerInfoRepository;

	@GetMapping("/download/get")
	public ResponseEntity<EfgsConfig> getDownloadConfig() {
		EfgsWorkerInfo info = efgsWorkerInfoRepository.getEfgsWorkerInfo(EfgsWorkerInfo.OperationType.DOWNLOAD);
		
		EfgsConfig efgsConfig = new EfgsConfig();
		efgsConfig.setSkippedCountries(info.getSkippedCounties());
				
		return new ResponseEntity<EfgsConfig>(efgsConfig, HttpStatus.OK);
	}
	
	@PostMapping("/download/set")
	public ResponseEntity<String> setDownloadConfig(@RequestBody EfgsConfig efgsConfig) {
		EfgsWorkerInfo efgsWorkerInfo = efgsWorkerInfoRepository.getEfgsWorkerInfo(EfgsWorkerInfo.OperationType.DOWNLOAD);
		if (efgsConfig != null) {
			if (EfgsConfig.isValidCountries(efgsConfig.getSkippedCountries())) {
				efgsWorkerInfo.setSkippedCounties(efgsConfig.getSkippedCountries());
				
				efgsWorkerInfo = efgsWorkerInfoRepository.save(efgsWorkerInfo);
				return new ResponseEntity<String>(HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}


}
