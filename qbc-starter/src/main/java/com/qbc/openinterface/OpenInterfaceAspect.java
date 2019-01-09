package com.qbc.openinterface;

import java.util.Optional;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qbc.utils.core.UserUtils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class OpenInterfaceAspect {

	private static final String LOG_PATTEN = String.join(System.lineSeparator(), "", //
			"┏━━━━━开放接口调用结束━━━━━", //
			"┣ 结果代码：{}", //
			"┣ 结果消息：{}", //
			"┣ 结果内容：{}", //
			"┗━━━━━开放接口调用结束━━━━━", "");

	@Autowired
	private ObjectMapper objectMapper;

	@Pointcut("execution(public * com.qbc.openinterface.OpenInterfaceController.*(..))")
	public void pointcut() {
	}

	@AfterReturning(value = "pointcut()", returning = "returnValue")
	@SneakyThrows
	public void afterReturning(JoinPoint joinPoint, Object returnValue) {
		if (log.isDebugEnabled() && returnValue instanceof OpenInterfaceResponse<?>) {
			OpenInterfaceResponse<?> openInterfaceResponse = (OpenInterfaceResponse<?>) returnValue;
			log.debug(LOG_PATTEN, openInterfaceResponse.getCode(), openInterfaceResponse.getMessage(),
					Optional.ofNullable(openInterfaceResponse.getData()).map(this::writeValueAsString).orElse(""));
		}
		UserUtils.removeAll();
	}

	@SneakyThrows
	private String writeValueAsString(Object value) {
		return objectMapper.writeValueAsString(value);
	}

}
