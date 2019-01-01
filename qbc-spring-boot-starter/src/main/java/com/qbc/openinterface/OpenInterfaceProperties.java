package com.qbc.openinterface;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@PropertySource(value = "classpath:qbc-open-interface.properties", encoding = "UTF-8")
@ConfigurationProperties("qbc.open-interface")
public class OpenInterfaceProperties {

	/**
	 * 启用开放接口
	 */
	private boolean enable;

	/**
	 * 开放接口的访问路径
	 */
	private String path;

}
