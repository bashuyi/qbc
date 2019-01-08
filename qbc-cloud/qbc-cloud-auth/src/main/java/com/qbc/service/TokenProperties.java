package com.qbc.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties("qbc.token")
@PropertySource(value = "classpath:token.yml", encoding = "UTF-8")
public class TokenProperties {
	
	/**
	 * 签发人
	 */
	private String issuer = "qbc";

}
