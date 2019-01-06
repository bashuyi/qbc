package com.qbc.config.cloud;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateCloudConfig {

	@Bean
	@LoadBalanced
	@ConditionalOnMissingBean(RestTemplate.class)
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
