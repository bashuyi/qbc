package com.qbc.api.core;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qbc.exception.NoLoginException;
import com.qbc.exception.NoPermissionException;

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
	public Object around(ProceedingJoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		long startTime = System.currentTimeMillis();
		ResultBean<?> resultBean = new ResultBean<>();
		try {
			resultBean = (ResultBean<?>) ObjectUtils.defaultIfNull(joinPoint.proceed(), new ResultBean<>());
		} catch (ConstraintViolationException e) {
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
			log.error(String.format("%s.%s未知异常", signature.getDeclaringTypeName(), signature.getName()), e);
			resultBean = new ResultBean<>(ResultBean.FAILURE, e.getMessage());
		} finally {
			if (resultBean.isSuccess() == false) {
				log.error(getMessage(joinPoint, startTime, resultBean));
			} else if (log.isDebugEnabled()) {
				log.debug(getMessage(joinPoint, startTime, resultBean));
			}
		}
		return resultBean;
	}

	@SneakyThrows
	private String getMessage(ProceedingJoinPoint joinPoint, long startTime, ResultBean<?> resultBean) {
		Signature signature = joinPoint.getSignature();
		String className = signature.getDeclaringTypeName();
		String methodName = signature.getName();
		String args = ArrayUtils.toString(joinPoint.getArgs());
		String data = objectMapper.writeValueAsString(resultBean.getData());

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
		message.append(String.format("┣ 结果内容：%s", data));
		message.append(System.lineSeparator());
		message.append(String.format("┗━━━━━ [%s.%s] ━━━", className, methodName));
		message.append(System.lineSeparator());
		return message.toString();
	}

}
