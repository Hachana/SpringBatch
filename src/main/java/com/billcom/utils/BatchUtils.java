package com.billcom.utils;

import java.net.URI;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.billcom.model.BatchBean;
import com.billcom.model.JbpmReengagement;
import com.billcom.model.RestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.*;
@Slf4j
@PropertySource({ "classpath:batchConfig.properties" })
@Component
public class BatchUtils {
	private Logger log = LoggerFactory.getLogger(BatchUtils.class);
	@Value("${pbiJbpmRestUsername}")
	String pbiJbpmRestUsername;

	@Value("${pbiJbpmRestPassword}")
	String pbiJbpmRestPassword;

	@Value("${pbiJbpmWsStartReeRestUrl}")
	String pbiJbpmWsStartReeRestUrl;
	
	
	public BatchBean consumeRestApiPostStartReengagementJbpm(JbpmReengagement newJbpmReengagement) throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(org.springframework.http.MediaType.APPLICATION_JSON));
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		String username = pbiJbpmRestUsername;
		String password = pbiJbpmRestPassword;
		URI url = new URI(pbiJbpmWsStartReeRestUrl);
		BatchBean batchBean = new BatchBean();
		try {
			String AthorizationString = "Basic " + new String(org.apache.commons.codec.binary.Base64
					.encodeBase64(new String(username + ":" + password).getBytes()));
			headers.add("Authorization", AthorizationString);

			RestTemplate restTemplate = new RestTemplate();

			// Data attached to the request.
			// Send request with POST method.
			HttpEntity<JbpmReengagement> requestBody = new HttpEntity<>(newJbpmReengagement, headers);
			ResponseEntity<String> result = restTemplate.postForEntity(url, requestBody, String.class);

			log.info("==> CustomerId after call rest :: " + requestBody.getBody().getCustId());

			String results = result.getBody();
			ObjectMapper objectMapper = new ObjectMapper();
			RestResponse restResponse = objectMapper.readValue(results, RestResponse.class);
			log.info("restResponse toString -> " + restResponse.toString());
			batchBean.setRestResponse(restResponse);

			log.info("=> result of rest JBPMStart ws :: " + result.getBody() + ", comment :: "
					+ batchBean.getRestResponse().getComment() + " , ErrorCode :: "
					+ batchBean.getRestResponse().getErrorCode() + " , isIssuccessful :: "
					+ batchBean.getRestResponse().isIssuccessful() + " , Id :: " + batchBean.getRestResponse().getId());

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return batchBean;

	}

}
