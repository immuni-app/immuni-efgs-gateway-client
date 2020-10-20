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
import org.apache.commons.codec.binary.Base64;
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
	@GetMapping("/download/{batchDate}/{batchTag}")
	public String download(@PathVariable("batchDate") String batchDate, @PathVariable("batchTag") String batchTag) {
		log.info("Download INFO before sending -> batchDate: {} - batchTag: {}", batchDate, batchTag);
		String nextBatchTag = null;
		 String batchTagFound = batchTag;
		try {
			boolean verifica = false;
			StringBuffer text = new StringBuffer();
			Map<String, Integer> countTotal = new HashMap<String, Integer>();
			RestApiResponse<EfgsProto.DiagnosisKeyBatch> resp = client.download(batchDate, batchTag);

			if (resp.getStatusCode() == RestApiClient.DOWNLOAD_STATUS_RETURNS_BATCH_200) {
				log.info("Start processing downloaded keys -> batchDate: {} - batchTag: {}", batchDate, batchTag);
			    nextBatchTag = resp.getNextBatchTag();

			    batchTagFound = resp.getBatchTag();

			    RestApiResponse<List<Audit>> respAudit = client.audit(batchDate, batchTagFound);
				List<Audit> auditList = respAudit.getData();

			    if (resp.getData() != null) {
					
					EfgsProto.DiagnosisKeyBatch protoDiagnosisKeyBatch = resp.getData();

				    Map<String, List<EfgsKey>> efgsKeyPerOriginMap = DiagnosisKeyMapper.protoToEfgsKeyPerOrigin(protoDiagnosisKeyBatch.getKeysList());
					log.info("Breakdown of UE keys by origin to verify the signature");
					
					Map<String, List<Audit>> auditToMapPerCounty = auditToMapPerCounty(auditList);
					
					for (String country : auditToMapPerCounty.keySet()) {
						if (!country.equalsIgnoreCase("IT")) {
							List<Audit> auditListInner =  auditToMapPerCounty.get(country);
					    	List<EfgsKey> efgsKeyPerOrigin = efgsKeyPerOriginMap.get(country);
					    	int startIndex = 0;
					    	int count = 0;
						    for (Audit audit: auditListInner) {
						    	count = count + 1;
						    	List<EfgsKey> efgsKeyPerOriginInner = new ArrayList<EfgsKey>();
						    	for (int index=startIndex; index<efgsKeyPerOrigin.size(); index++) {
						    		efgsKeyPerOriginInner.add(efgsKeyPerOrigin.get(index));
						    		if (efgsKeyPerOriginInner.size()>=audit.getAmount()) {
						    			startIndex = index + 1;
						    			break;
						    		}
						    		
						    	}
						    	
						    	boolean verifiedSign = batchSignatureVerifier.validateDiagnosisKeyWithSignature(efgsKeyPerOriginInner, audit);
								log.info("Signature verified: {} - batchDate: {} - batchTag: {} - country: {} - block: {}", 
										verifiedSign, batchDate, batchTag, country, count);
								
						    	verifica = verifica || verifiedSign;
	
						    }
						}
					}

				    for (List<EfgsKey> entities: efgsKeyPerOriginMap.values()) {
						for (EfgsKey e: entities) {
							text.append("Key: ").append(new String(e.getKeyData())).append("\n\r");
						}
						countTotal.put(entities.get(0).getOrigin(), entities.size());
				    }

				}
				log.info("End processing downloaded keys");
			} else {
				log.info("Warning! Response code: {} - batchDate: {} - batchTag: {}", resp.getStatusCode().toString(), batchDate, batchTag);
			}

			StringBuffer buffer = new StringBuffer();
			buffer.append("<html>");
			buffer.append("<body>");
			buffer.append("<h1>Download:</h1>");
			buffer.append("<p> Date: ").append(batchDate).append("</p>");
			buffer.append("<p> Tag batch: ").append(batchTagFound).append("</p>");
			buffer.append("<p> Next tag batch: ").append(nextBatchTag).append("</p>");

			buffer.append("<p> Count: ").append(countTotal.toString()).append("</p>");
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
	
	private Map<String, List<Audit>> auditToMapPerCounty(List<Audit> audits) {
		Map<String, List<Audit>> auditToMapPerCounty = new HashMap<String, List<Audit>>();
		for (Audit audit : audits) {
			if (!auditToMapPerCounty.containsKey(audit.getCountry())) {
				auditToMapPerCounty.put(audit.getCountry(), new ArrayList<Audit>());
			}
			auditToMapPerCounty.get(audit.getCountry()).add(audit);
		}
		
		return auditToMapPerCounty;
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

	
	
	@GetMapping("/keys")
	public String keys() {
		StringBuffer content = new StringBuffer();
		try {
		    
	    	Calendar calendar = new GregorianCalendar();
	    	calendar.setTime(new Date());
		    for (int i=0; i<20; i++) {
		    	long rsp =  calendar.getTimeInMillis() / 1000 / 600;
		    	StringBuffer key = new StringBuffer();
		    	key.append("{")
		    	.append("\"rolling_period\": 144, ")
		    	.append("\"key_data\": \"").append(byteToBase64(getRandomKeyData())).append("\", ")
		    	.append("\"rolling_start_number\": ").append(rsp)
		    	.append("},");
		    	content.append(key.toString()).append("\n");
			    calendar.add(Calendar.DAY_OF_MONTH, -1);
		    }

		    log.info(content.toString());	
			StringBuffer buffer = new StringBuffer();
			buffer.append("<html>");
			buffer.append("<body>");
			
			buffer.append("<textarea rows=\"30\" cols=\"100\">").append( content.toString() ).append("</textarea>");

			buffer.append("</body>");
			buffer.append("</html>");
			
			return buffer.toString();
		
		} catch (Exception e) {
			log.error("ERRORE", e);
			content.append("Errore: ").append(e.getMessage()).append("<br>");
		}
		return "ERROR";		
	}
	public static String byteToBase64(byte[] bytes) {
		Base64 base64Url = new Base64();
		return base64Url.encodeAsString(bytes);
	}
	
	
}
