package com.qbc.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

/**
 * API配置，用于开启API和指定API的访问路径
 *
 * @author Ma
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("qbc.api")
@PropertySource(value = "classpath:qbc-api.yml", encoding = "UTF-8")
public class ApiProperties {

	/** 启用开放接口 */
	private boolean enable;

	/** API的访问路径，默认为api */
	private String path;

}
