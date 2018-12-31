package com.qbc.api.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties("qbc.api")
public class ApiProperties {

	/**
	 * 外部接口URL
	 */
	private String url = "api";

}
