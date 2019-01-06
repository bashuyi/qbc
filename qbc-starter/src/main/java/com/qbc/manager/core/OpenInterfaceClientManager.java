package com.qbc.manager.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qbc.openinterface.OpenInterfaceRequest;
import com.qbc.openinterface.OpenInterfaceResponse;

import lombok.SneakyThrows;

@Component
public class OpenInterfaceClientManager {

	@Autowired(required = false)
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@SneakyThrows
	public <T> OpenInterfaceResponse<T> post(String url, OpenInterfaceRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<OpenInterfaceRequest> httpEntity = new HttpEntity<>(request, headers);
		String response = restTemplate.postForObject(url, httpEntity, String.class);
		return objectMapper.readValue(response, new TypeReference<OpenInterfaceResponse<T>>() {
		});
	}

}
