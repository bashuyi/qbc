package com.qbc.config.core;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Bean
	@ConditionalOnMissingBean(RestTemplate.class)
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
