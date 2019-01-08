package com.qbc.manager.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.qbc.manager.core.OpenInterfaceClientManager;
import com.qbc.openinterface.OpenInterfaceRequest;
import com.qbc.openinterface.OpenInterfaceResponse;

@Component
public class OpenInterfaceClientCloudManager {

	@Autowired
	private OpenInterfaceClientManager openInterfaceClientManager;

	@HystrixCommand(fallbackMethod = "postFallback")
	public <T> OpenInterfaceResponse<T> post(String serviceName, OpenInterfaceRequest request) {
		String url = String.format("http://%s/api", serviceName);
		return openInterfaceClientManager.post(url, request);
	}

	public <T> OpenInterfaceResponse<T> postFallback(String serviceName, OpenInterfaceRequest request) {
		return OpenInterfaceResponse.hystrix();
	}

}
