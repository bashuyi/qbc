package com.qbc.manager.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qbc.api.ApiRequest;
import com.qbc.api.ApiResponse;

import lombok.SneakyThrows;

/**
 * API客户端
 *
 * @author Ma
 */
@Component
public class ApiClientManager {

	@Autowired(required = false)
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@SneakyThrows
	public <T> ApiResponse<T> post(String url, ApiRequest apiRequest) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<ApiRequest> httpEntity = new HttpEntity<>(apiRequest, headers);
		String response = restTemplate.postForObject(url, httpEntity, String.class);
		return objectMapper.readValue(response, new TypeReference<ApiResponse<T>>() {
		});
	}

}
