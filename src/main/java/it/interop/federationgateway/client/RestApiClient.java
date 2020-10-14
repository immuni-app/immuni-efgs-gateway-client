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
package it.interop.federationgateway.client;

import java.util.List;

import it.interop.federationgateway.client.base.RestApiException;
import it.interop.federationgateway.client.base.RestApiResponse;
import it.interop.federationgateway.model.Audit;
import it.interop.federationgateway.model.EfgsProto;

public interface RestApiClient {
	public static String NEXT_BATCH_TAG = "nextBatchTag";
	public static String BATCH_TAG = "batchTag";

	public RestApiResponse<EfgsProto.DiagnosisKeyBatch> download(String date, String batchTag) throws RestApiException;
	public RestApiResponse<String> upload(String batchTag, String batchSignature, EfgsProto.DiagnosisKeyBatch protoBatch) throws RestApiException;
	public RestApiResponse<List<Audit>> audit(String date, String batchTag) throws RestApiException;

}
