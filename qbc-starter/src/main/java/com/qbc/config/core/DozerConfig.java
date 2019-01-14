package com.qbc.config.core;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 对象深拷贝配置
 *
 * @author Ma
 */
@Configuration
public class DozerConfig {

	@Bean
	DozerBeanMapper dozerBeanMapper() {
		return new DozerBeanMapper();
	}

}
