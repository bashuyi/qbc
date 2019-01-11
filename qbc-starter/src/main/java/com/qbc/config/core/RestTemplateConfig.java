package com.qbc.config.core;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * RestTemplate配置
 *
 * @author Ma
 */
@Slf4j
@Configuration
public class RestTemplateConfig {

	@Bean
	@ConditionalOnMissingBean(RestTemplate.class)
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		log.debug("没有自定义RestTemplate，加载默认的Bean。");
		return builder.build();
	}

}
