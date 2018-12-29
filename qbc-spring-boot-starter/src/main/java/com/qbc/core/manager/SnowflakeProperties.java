package com.qbc.core.manager;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties
public class SnowflakeProperties {

	/** 工作机器ID,数据范围为0~31 **/
	private long workerId;

	/** 数据中心ID,数据范围为0~31 **/
	private long dataCenterId;

	/** 起始时间戳，用于用当前时间戳减去这个时间戳，算出偏移量(默认从2015-01-01起) **/
	private long startTime = 1420041600000L;

}
