package com.qbc.api;

import java.util.Optional;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qbc.constant.LogPatten;
import com.qbc.utils.core.UserUtils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * API的AOP，用于打印日志和移除每次请求上下文的用户信息。
 *
 * @author Ma
 */
@Slf4j
@Aspect
@Component
public class ApiAspect {

	@Autowired
	private ObjectMapper objectMapper;

	@Pointcut("execution(public * com.qbc.api.ApiController.*(..))")
	public void pointcut() {
	}

	@AfterReturning(value = "pointcut()", returning = "returnValue")
	@SneakyThrows
	public void afterReturning(JoinPoint joinPoint, Object returnValue) {
		// 打印响应内容
		if (log.isDebugEnabled() && returnValue instanceof ApiResponse<?>) {
			ApiResponse<?> openInterfaceResponse = (ApiResponse<?>) returnValue;
			log.debug(LogPatten.API_END, openInterfaceResponse.getCode(), openInterfaceResponse.getMessage(),
					Optional.ofNullable(openInterfaceResponse.getData()).map(this::writeValueAsString).orElse(""));
		}
		// 上下文的用户信息通过ThreadLocal实现，由于线程池重用的关系，必须每次请求结束清除。
		UserUtils.removeAll();
	}

	@SneakyThrows
	private String writeValueAsString(Object value) {
		return objectMapper.writeValueAsString(value);
	}

}
