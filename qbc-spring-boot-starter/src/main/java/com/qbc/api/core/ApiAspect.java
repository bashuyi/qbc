package com.qbc.api.core;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qbc.exception.NoLoginException;
import com.qbc.exception.NoPermissionException;
import com.qbc.exception.ValidException;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class ApiAspect {

	@Autowired
	private ObjectMapper objectMapper;

	@Pointcut("execution(public com.qbc.api.core.ResultBean *(..))")
	public void pointcut() {
	}

	@Around("pointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		String className = joinPoint.getSignature().getDeclaringTypeName();
		String methodName = joinPoint.getSignature().getName();
		String args = ArrayUtils.toString(joinPoint.getArgs());
		long startTime = System.currentTimeMillis();
		ResultBean<?> resultBean = new ResultBean<>();
		try {
			resultBean = (ResultBean<?>) ObjectUtils.defaultIfNull(joinPoint.proceed(), new ResultBean<>());
		} catch (ValidException e) {
			// 校验异常
			resultBean = new ResultBean<>(ResultBean.INVALID, e.getMessage());
		} catch (NoLoginException e) {
			// 未登录异常
			resultBean = new ResultBean<>(ResultBean.NO_LOGIN, e.getMessage());
		} catch (NoPermissionException e) {
			// 没有权限异常
			resultBean = new ResultBean<>(ResultBean.NO_PERMISSION, e.getMessage());
		} catch (Throwable e) {
			// 未知异常
			// TODO 发邮件等方式通知开发人员
			log.error(String.format("%s.%s未知异常", className, methodName), e);
			resultBean = new ResultBean<>(ResultBean.FAILURE, e.getMessage());
		} finally {
			if (resultBean.isSuccess()) {
				if (log.isDebugEnabled()) {
					log.debug(getMessage(className, methodName, args, startTime, resultBean));
				}
			} else {
				log.error(getMessage(className, methodName, args, startTime, resultBean));
			}
		}
		return resultBean;
	}

	@SneakyThrows
	private String getMessage(String className, String methodName, String args, long startTime,
			ResultBean<?> resultBean) {
		StringBuffer message = new StringBuffer();
		message.append(System.lineSeparator());
		message.append(String.format("┏━━━━━ [%s.%s] ━━━", className, methodName));
		message.append(System.lineSeparator());
		message.append(String.format("┣ 请求参数：%s", args));
		message.append(System.lineSeparator());
		message.append(String.format("┣ 请求时间：%dms", System.currentTimeMillis() - startTime));
		message.append(System.lineSeparator());
		message.append(String.format("┣ 结果代码：%d", resultBean.getCode()));
		message.append(System.lineSeparator());
		message.append(String.format("┣ 结果消息：%s", resultBean.getMessage()));
		message.append(System.lineSeparator());
		message.append(String.format("┣ 结果内容：%s", objectMapper.writeValueAsString(resultBean.getData())));
		message.append(System.lineSeparator());
		message.append(String.format("┗━━━━━ [%s.%s] ━━━", className, methodName));
		message.append(System.lineSeparator());
		return message.toString();
	}

}
