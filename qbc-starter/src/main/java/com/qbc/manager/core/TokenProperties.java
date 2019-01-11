package com.qbc.manager.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * Token属性
 *
 * @author Ma
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("qbc.token")
public class TokenProperties {

	/**
	 * 签发人
	 */
	private String issuer = "qbc";

	/**
	 * 几小时后过期
	 */
	private int expiresAfterHours = 36;

}
