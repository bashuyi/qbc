package com.qbc.core.manager;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties
public class SnowFlakeProperties {

	/**
	 * 数据中心 0~1023
	 */
	private long datacenterId;

	/**
	 * 机器标识 0~1023
	 */
	private long machineId;

}
