package com.qbc.config.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * JPA配置属性
 *
 * @author Ma
 */
@Getter
@Setter
@ConfigurationProperties("qbc.jpa")
public class JpaProperties {

	/**
	 * 数据访问层基包
	 */
	private String basePackage;

}
