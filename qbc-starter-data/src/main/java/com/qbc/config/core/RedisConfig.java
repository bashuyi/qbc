package com.qbc.config.core;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Redis配置，使用JSON格式序列化和反序列化
 *
 * @author Ma
 */
@Configuration
@EnableCaching
public class RedisConfig {

	@Bean
	@ConditionalOnMissingBean(KeyGenerator.class)
	KeyGenerator reflectKey() {
		return (target, method, params) -> {
			String className = target.getClass().getName();
			String methodName = method.getName();
			String paramsValue = StringUtils.join(params, "_");
			return String.join("#", String.join(".", className, methodName), paramsValue);
		};
	}

	@Bean
	@ConditionalOnMissingBean(CacheManager.class)
	CacheManager cacheManager(RedisConnectionFactory factory) {
		// 序列化生成的JSON必须带上class类型，否则反序列化时会发生类型转换异常
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		// Java8时间格式序列化
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.registerModule(new JavaTimeModule());

		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(
				objectMapper);

		// 配置序列化
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
				.serializeKeysWith(SerializationPair.fromSerializer(stringRedisSerializer))
				.serializeValuesWith(SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer))
				.disableCachingNullValues();

		return RedisCacheManager.builder(factory).cacheDefaults(config).build();
	}

}
