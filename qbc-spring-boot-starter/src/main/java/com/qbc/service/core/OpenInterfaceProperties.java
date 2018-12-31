package com.qbc.service.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties("qbc.open-interface")
public class OpenInterfaceProperties {

	/**
	 * 开放接口的访问路径
	 */
	private String path;

}
