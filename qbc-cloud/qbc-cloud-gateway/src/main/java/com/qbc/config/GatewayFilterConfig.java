package com.qbc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qbc.filter.VerifyTokenGatewayFilterFactory;
import com.qbc.manager.cloud.ApiClientCloudManager;

/**
 * 网关过滤器配置
 *
 * @author Ma
 */
@Configuration
public class GatewayFilterConfig {

	@Bean
	VerifyTokenGatewayFilterFactory verifyTokenGatewayFilterFactory(ApiClientCloudManager apiClientCloudManager,
			ObjectMapper objectMapper) {
		VerifyTokenGatewayFilterFactory verifyTokenGatewayFilterFactory = new VerifyTokenGatewayFilterFactory();
		verifyTokenGatewayFilterFactory.setApiClientCloudManager(apiClientCloudManager);
		verifyTokenGatewayFilterFactory.setObjectMapper(objectMapper);
		return verifyTokenGatewayFilterFactory;
	}

}
