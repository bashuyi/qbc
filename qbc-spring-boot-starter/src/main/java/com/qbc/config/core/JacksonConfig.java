package com.qbc.config.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfig {

	@Bean
	ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		// 反序列化的时候如果多了其他属性,不抛出异常
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 如果是空对象的时候,不抛异常
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		// 忽略空值
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// Java8时间格式序列化
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.registerModule(new JavaTimeModule());
		return objectMapper;
	}

}
