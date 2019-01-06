package com.qbc.manager.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qbc.openinterface.OpenInterfaceRequest;
import com.qbc.openinterface.OpenInterfaceResponse;

import lombok.SneakyThrows;

@Component
public class OpenInterfaceClientManage {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@SneakyThrows
	public <T> OpenInterfaceResponse<T> post(String url, OpenInterfaceRequest request) {
		String response = restTemplate.postForObject(url, request, String.class);
		return objectMapper.readValue(response, new TypeReference<OpenInterfaceResponse<T>>() {
		});
	}

}
