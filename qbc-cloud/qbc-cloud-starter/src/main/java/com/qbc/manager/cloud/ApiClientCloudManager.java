package com.qbc.manager.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.qbc.api.ApiRequest;
import com.qbc.api.ApiResponse;
import com.qbc.manager.core.ApiClientManager;

/**
 * API客户端处理，支持负载均衡和熔断
 *
 * @author Ma
 */
@Component
public class ApiClientCloudManager {

	@Autowired
	private ApiClientManager openInterfaceClientManager;

	@HystrixCommand(fallbackMethod = "postFallback")
	public <T> ApiResponse<T> post(String applicationName, ApiRequest request) {
		String url = String.format("http://%s/api", applicationName);
		return openInterfaceClientManager.post(url, request);
	}

	public <T> ApiResponse<T> postFallback(String applicationName, ApiRequest request) {
		return ApiResponse.hystrix(String.format("%s unavailable", applicationName));
	}

}
