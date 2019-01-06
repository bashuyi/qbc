package com.qbc.config.cloud;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RestTemplateCloudConfig {

	@Bean
	@LoadBalanced
	@ConditionalOnMissingBean(RestTemplate.class)
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		log.debug("没有自定义RestTemplate，加载默认的负载均衡的Bean。");
		return builder.build();
	}

}
