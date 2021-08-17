package com.farmersbyte.enterprise.usermodule.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class RestClient {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public <T> T postForOauth(String url, String username, String password, T request, MediaType mediaType,
			Class<T> clazz) throws Exception {
		try {
			ResponseEntity<T> responseEntity = null;
			if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
				HttpHeaders headers = createHeaders(username, password);
				if (mediaType != null) {
					headers.setContentType(mediaType);
				}
					responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<T>(request, headers),
							clazz);
			} else {
				responseEntity = restTemplate.postForEntity(url, request, clazz);
			}
			if (responseEntity != null) {
				return responseEntity.getBody();
			}
		} catch (Exception e) {
			throw new Exception("Invalid user authentication", e);
		}
		return null;
	}
	
	public <T> T getForObject(String url, Map<String, String> params, Class<T> clazz) {

		T response = null;
		try {

			ResponseEntity<String> jsonResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity.get(),
					String.class, params);
			if (jsonResponse != null && StringUtils.isNotEmpty(jsonResponse.getBody()))
				response = objectMapper.readValue(jsonResponse.getBody(), clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}
	
	@SuppressWarnings("serial")
	HttpHeaders createHeaders(String username, String password) {
		return new HttpHeaders() {
			{
				String auth = username + ":" + password;
				byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};
	}
	
	Supplier<HttpHeaders> buildHttpHeaders = () -> {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return httpHeaders;
	};

	Supplier<HttpEntity<String>> httpEntity = () -> new HttpEntity<String>(buildHttpHeaders.get());

}
