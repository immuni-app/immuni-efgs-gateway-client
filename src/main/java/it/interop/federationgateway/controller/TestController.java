/*-
 *   Copyright (C) 2020 Presidenza del Consiglio dei Ministri.
 *   Please refer to the AUTHORS file for more information. 
 *   This program is free software: you can redistribute it and/or modify 
 *   it under the terms of the GNU Affero General Public License as 
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *   This program is distributed in the hope that it will be useful, 
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of 
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 *   GNU Affero General Public License for more details.
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program. If not, see <https://www.gnu.org/licenses/>.   
 */
package it.interop.federationgateway.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.interop.federationgateway.batchsigning.BatchSignatureUtils;
import it.interop.federationgateway.batchsigning.BatchSignatureVerifier;
import it.interop.federationgateway.batchsigning.SignatureGenerator;
import it.interop.federationgateway.client.RestApiClient;
import it.interop.federationgateway.client.base.RestApiException;
import it.interop.federationgateway.client.base.RestApiResponse;
import it.interop.federationgateway.entity.UploadEuRaw;
import it.interop.federationgateway.mapper.DiagnosisKeyMapper;
import it.interop.federationgateway.model.Audit;
import it.interop.federationgateway.model.EfgsKey;
import it.interop.federationgateway.model.EfgsKey.ReportType;
import it.interop.federationgateway.model.EfgsProto;
import it.interop.federationgateway.worker.EfgsWorker;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {

	@Autowired(required=true)
	RestApiClient client;

	@Autowired(required=true)
	private SignatureGenerator signatureGenerator;
	
	@Autowired(required=true)
	private BatchSignatureVerifier batchSignatureVerifier;

	@Autowired(required=true)
	private EfgsWorker efgsWorker;

	@GetMapping("/test")
	public ResponseEntity<String> test() {
		StringBuffer content = new StringBuffer();
		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH, -Integer.parseInt("2"));
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			content.append("Errore: ").append(e.getMessage()).append("<br>");
		}
		return new ResponseEntity<String>(content.toString(), HttpStatus.OK);		
	}

	@GetMapping("/testUpload")
	public ResponseEntity<String> testUpload() {
		StringBuffer content = new StringBuffer();
		try {
			
			efgsWorker.uploadWorker();
			log.info("OK");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			content.append("Errore: ").append(e.getMessage()).append("<br>");
		}
		return new ResponseEntity<String>(content.toString(), HttpStatus.OK);		
	}


	@GetMapping("/testDownload")
	public ResponseEntity<String> testDownload() {
		StringBuffer content = new StringBuffer();
		try {
			
			efgsWorker.downloadWorker();
			log.info("OK");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			content.append("Errore: ").append(e.getMessage()).append("<br>");
		}
		return new ResponseEntity<String>(content.toString(), HttpStatus.OK);		
	}

	@GetMapping("/testSign")
	public ResponseEntity<String> testSign() {
		StringBuffer content = new StringBuffer();
		try {
			String countryOrigin = "IT";
		    List<EfgsKey> entities = new ArrayList<EfgsKey>();
		    
		    for (int i=0; i<100; i++) {
		    	Calendar calendar = new GregorianCalendar();
		    	calendar.setTime(new Date());
			    calendar.add(Calendar.DAY_OF_MONTH, -1);
		    	
		    	long rsp =  calendar.getTimeInMillis() / 1000 / 600;
		    	
			    EfgsKey key1 = new EfgsKey(getRandomKeyData(), (int)rsp, 144, 3, Arrays.asList("ES,DE,DK".split(",")), ReportType.SELF_REPORT, 1, countryOrigin);
			    
			    calendar.add(Calendar.DAY_OF_MONTH, -1);
			    rsp =  calendar.getTimeInMillis() / 1000 / 600;
			    
			    EfgsKey key2 = new EfgsKey(getRandomKeyData(), (int)rsp, 144, 3, Arrays.asList("ES,DK".split(",")), ReportType.CONFIRMED_CLINICAL_DIAGNOSIS, 2, countryOrigin);

			    calendar.add(Calendar.DAY_OF_MONTH, -1);
			    rsp =  calendar.getTimeInMillis() / 1000 / 600;

			    EfgsKey key3 = new EfgsKey(getRandomKeyData(), (int)rsp, 144, 3, Arrays.asList("ES,DE".split(",")), ReportType.CONFIRMED_CLINICAL_DIAGNOSIS, 3, countryOrigin);
			    
			    
			    entities.add(key1);
			    entities.add(key2);
			    entities.add(key3);
		    }

		    EfgsProto.DiagnosisKeyBatch protoBatch = EfgsProto.DiagnosisKeyBatch.newBuilder()
		    	      .addAllKeys(DiagnosisKeyMapper.efgsKeyToProto(entities))
		    	      .build();

	        String batchSignature = signatureGenerator.getSignatureForBytes(
	                BatchSignatureUtils.generateBytesToVerify(protoBatch));
			
		    content.append("BatchSignature: ").append(batchSignature).append("<br>");

		    log.info(content.toString());	
		
		} catch (Exception e) {
			log.error("ERRORE", e);
			content.append("Errore: ").append(e.getMessage()).append("<br>");
		}
		return new ResponseEntity<String>(content.toString(), HttpStatus.OK);		
	}

	@GetMapping("/upload/{batchTag}")
	public ResponseEntity<String> upload(@PathVariable("batchTag") String batchTag) {
		StringBuffer content = new StringBuffer();
		String countryOrigin = "IT";
		try {
			
		    List<EfgsKey> entities = new ArrayList<EfgsKey>();
		    
		    for (int i=0; i<100; i++) {
		    	Calendar calendar = new GregorianCalendar();
		    	calendar.setTime(new Date());
			    calendar.add(Calendar.DAY_OF_MONTH, -1);
		    	
		    	long rsp =  calendar.getTimeInMillis() / 1000 / 600;
		    	
			    EfgsKey key1 = new EfgsKey(getRandomKeyData(), (int)rsp, 144, 3, Arrays.asList("ES,DE,DK".split(",")), ReportType.SELF_REPORT, 1, countryOrigin);
			    
			    calendar.add(Calendar.DAY_OF_MONTH, -1);
			    rsp =  calendar.getTimeInMillis() / 1000 / 600;
			    
			    EfgsKey key2 = new EfgsKey(getRandomKeyData(), (int)rsp, 144, 3, Arrays.asList("ES,DK".split(",")), ReportType.CONFIRMED_CLINICAL_DIAGNOSIS, 2, countryOrigin);

			    calendar.add(Calendar.DAY_OF_MONTH, -1);
			    rsp =  calendar.getTimeInMillis() / 1000 / 600;

			    EfgsKey key3 = new EfgsKey(getRandomKeyData(), (int)rsp, 144, 3, Arrays.asList("ES,DE".split(",")), ReportType.CONFIRMED_CLINICAL_DIAGNOSIS, 3, countryOrigin);
			    
			    
			    entities.add(key1);
			    entities.add(key2);
			    entities.add(key3);
		    }

		    EfgsProto.DiagnosisKeyBatch protoBatch = EfgsProto.DiagnosisKeyBatch.newBuilder()
		    	      .addAllKeys(DiagnosisKeyMapper.efgsKeyToProto(entities))
		    	      .build();

		    
	        
	        String batchSignature = signatureGenerator.getSignatureForBytes(
	                BatchSignatureUtils.generateBytesToVerify(protoBatch));
	        
	        RestApiResponse<String> esito = client.upload(batchTag, batchSignature, protoBatch);
		    
		    content.append("Esito: ").append(esito.getData()).append("<br>");
		    content.append("BatchTag: ").append(batchTag).append("<br>");
		    for (EfgsKey e: entities) {
			    content.append("Key: ").append(new String(e.getKeyData())).append("<br>");
		    }
		    content.append("BatchSignature: ").append(batchSignature).append("<br>");

		    log.info(content.toString());	
		
		} catch (Exception e) {
			log.error("ERRORE", e);
			content.append("Errore: ").append(e.getMessage()).append("<br>");
		}
		return new ResponseEntity<String>(content.toString(), HttpStatus.OK);		
	}
	
	  public static byte[] getRandomKeyData() {
		  Random rd = new Random();
	      byte[] arr = new byte[16];
	      rd.nextBytes(arr);

		  return arr;
	  }
	
	
		
		@ResponseBody
		@GetMapping("/download/{date}/{batchTag}")
		public String download(@PathVariable("date") String date, @PathVariable("batchTag") String batchTag) {
			try {
				log.info("CIAOOOOOOO ----------------->");
				RestApiResponse<EfgsProto.DiagnosisKeyBatch> resp = client.download(date, "null".equalsIgnoreCase(batchTag)?null:batchTag);
				StringBuffer text = new StringBuffer();
				Map<String, Integer> count = new HashMap<String, Integer>();
				String batchTagFound = batchTag;
				boolean verifica = false;
				if (resp.getStatusCode() == HttpStatus.OK) {
					if (resp.getData() != null) {
						batchTagFound = resp.getHeaders().get("batchTag").get(0);
						EfgsProto.DiagnosisKeyBatch protoDiagnosisKeyBatch = resp.getData();
						

						RestApiResponse<List<Audit>> respAudit = client.audit(date, batchTagFound);
						
						List<Audit> auditList = respAudit.getData();
						

					    Map<String, List<EfgsKey>> efgsKeyPerOriginMap = DiagnosisKeyMapper.protoToEfgsKeyPerOrigin(protoDiagnosisKeyBatch.getKeysList());
					    
					    for (Audit audit: auditList) {
					    	List<EfgsKey> efgsKeyPerOrigin = efgsKeyPerOriginMap.get(audit.getCountry());
					    	boolean ver = batchSignatureVerifier.validateDiagnosisKeyWithSignature(efgsKeyPerOrigin, audit);
					    	
					    	UploadEuRaw diagnosisKeyEntity =DiagnosisKeyMapper.efgsKeysToEntity(efgsKeyPerOrigin, batchTagFound, 0);
					    	
					    	diagnosisKeyEntity.setVerifiedSign(ver);
					    	
					    	verifica = verifica || ver;

					    	
					    	log.info(audit.getCountry()+" Verifica firma: "+ver);

					    }
					    
					    log.info(" Verifica firma: "+verifica);
					    
					    
					    for (List<EfgsKey> entities: efgsKeyPerOriginMap.values()) {
							for (EfgsKey e: entities) {
								text.append("Key: ").append(new String(e.getKeyData())).append("\n\r");
							}
							count.put(entities.get(0).getOrigin(), entities.size());
					    }


					}
				}

				List<String> l = resp.getHeaders().get("nextBatchTag");

				StringBuffer buffer = new StringBuffer();
				buffer.append("<html>");
				buffer.append("<body>");
				buffer.append("<h1>Download:</h1>");
				buffer.append("<p> Date: ").append(date).append("</p>");
				buffer.append("<p> Tag batch: ").append(batchTagFound).append("</p>");
				buffer.append("<p> Next tag batch: ").append(l!=null?l.get(0):"fine").append("</p>");

				buffer.append("<p> Count: ").append(count.toString()).append("</p>");
				buffer.append("<p> Verifica firma: ").append(verifica).append("</p>");

				buffer.append("<textarea rows=\"30\" cols=\"100\">").append(resp.getData()!=null?resp.getData().toString():"empty").append("</textarea>");
				buffer.append("<textarea rows=\"30\" cols=\"100\">").append(text.toString()).append("</textarea>");
				
				buffer.append("</body>");
				buffer.append("</html>");
				
				return buffer.toString();
			} catch (Exception e) {
				log.error("ERRORE", e);
				return e.getMessage();
			}
		}

	@ResponseBody
	@GetMapping("/audit/{date}/{batchTag}")
	public String audit(@PathVariable("date") String date, @PathVariable("batchTag") String batchTag) {
		try {
			RestApiResponse<List<Audit>> resp = client.audit(date, "null".equalsIgnoreCase(batchTag)?null:batchTag);
			log.info("Response: ", resp);
			
			
			StringBuffer buffer = new StringBuffer();
			buffer.append("<html>");
			buffer.append("<body>");
			buffer.append("<h1>Audit:</h1>");
			buffer.append("<p> Date: ").append(date).append("</p>");
			buffer.append("<p> Tag batch: ").append(batchTag).append("</p>");

			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String txt = gson.toJson(resp.getData());
			
			buffer.append("<textarea rows=\"30\" cols=\"100\">").append( txt ).append("</textarea>");

			buffer.append("</body>");
			buffer.append("</html>");
			
			return buffer.toString();
		} catch (RestApiException e) {
			log.error("ERRORE", e);
		}
		return "ERROR";
	}

}
