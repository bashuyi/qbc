package com.qbc.manager.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * 应用配置
 *
 * @author Ma
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("spring.application")
public class ApplicationProperties {

	/** 应用名，用于服务注册与发现 */
	private String name;

	/** 应用表示名 */
	private String displayName;

	/** 应用描述 */
	private String description;

}
