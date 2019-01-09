package com.qbc.manager.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties("spring.application")
public class ResourceProperties {

	/**
	 * 应用名
	 */
	private String name;

	/**
	 * 应用描述
	 */
	private String description;

}
