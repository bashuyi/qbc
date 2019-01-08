package com.qbc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qbc.filter.VerifyTokenGatewayFilterFactory;
import com.qbc.manager.cloud.OpenInterfaceClientCloudManager;

@Configuration
public class GatewayFilterConfig {

	@Bean
	VerifyTokenGatewayFilterFactory verifyTokenGatewayFilterFactory(
			OpenInterfaceClientCloudManager openInterfaceClientCloudManager, ObjectMapper objectMapper) {
		VerifyTokenGatewayFilterFactory verifyTokenGatewayFilterFactory = new VerifyTokenGatewayFilterFactory();
		verifyTokenGatewayFilterFactory.setOpenInterfaceClientCloudManager(openInterfaceClientCloudManager);
		verifyTokenGatewayFilterFactory.setObjectMapper(objectMapper);
		return verifyTokenGatewayFilterFactory;
	}

}
