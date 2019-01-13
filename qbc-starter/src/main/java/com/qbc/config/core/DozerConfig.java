package com.qbc.config.core;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerConfig {

	@Bean
	DozerBeanMapper dozerBeanMapper() {
		return new DozerBeanMapper();
	}

}
