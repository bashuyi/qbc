package com.qbc.api.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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

	/**
	 * 拦截带有@Api注解的所有类的public方法
	 */
	@Pointcut("@within(com.qbc.api.core.Api) && execution(public * *(..))")
	public void pointcut() {
	}

	@Around("pointcut()")
	public Object around(ProceedingJoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		long startTime = System.currentTimeMillis();
		ApiResult<?> ApiResult = new ApiResult<>();
		try {
			Object returnVal = joinPoint.proceed();
			if (returnVal instanceof ApiResult<?>) {
				ApiResult = (ApiResult<?>) ObjectUtils.defaultIfNull(returnVal, new ApiResult<>());
			} else {
				ApiResult = new ApiResult<>(returnVal);
			}
		} catch (ConstraintViolationException e) {
			// 校验异常
			ApiResult = new ApiResult<>(ApiResult.INVALID, e.getMessage());
		} catch (NoLoginException e) {
			// 未登录异常
			ApiResult = new ApiResult<>(ApiResult.NO_LOGIN, e.getMessage());
		} catch (NoPermissionException e) {
			// 没有权限异常
			ApiResult = new ApiResult<>(ApiResult.NO_PERMISSION, e.getMessage());
		} catch (Throwable e) {
			// 未知异常
			// TODO 发邮件等方式通知开发人员
			log.error(String.format("%s.%s未知异常", signature.getDeclaringTypeName(), signature.getName()), e);
			ApiResult = new ApiResult<>(ApiResult.FAILURE, e.getMessage());
		} finally {
			if (ApiResult.isSuccess() == false) {
				log.error(getMessage(joinPoint, startTime, ApiResult));
			} else if (log.isDebugEnabled()) {
				log.debug(getMessage(joinPoint, startTime, ApiResult));
			}
		}
		return ApiResult;
	}

	@SneakyThrows
	private String getMessage(ProceedingJoinPoint joinPoint, long startTime, ApiResult<?> ApiResult) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		// 获得类名
		String className = signature.getDeclaringTypeName();
		// 获得方法名
		String methodName = signature.getName();

		// 获得接口名称
		Class<?> classType = signature.getDeclaringType();
		String simpleName = classType.getSimpleName();
		Api api = classType.getAnnotation(Api.class);
		String apiName = StringUtils.defaultIfEmpty(api.name(), simpleName);

		// 获得接口方法
		ApiMethod apiMethod = signature.getMethod().getAnnotation(ApiMethod.class);
		String apiMethodName = Optional.ofNullable(apiMethod).map(ApiMethod::name).orElse(methodName);

		// 获得接口参数
		String[] parameterNames = signature.getParameterNames();
		Object[] args = joinPoint.getArgs();
		Map<String, Object> parameterMap = new HashMap<>();
		for (int i = 0; i < args.length; i++) {
			parameterMap.put(parameterNames[i], args[i]);
		}
		String parameter = objectMapper.writeValueAsString(parameterMap);

		// 获得返回值
		String data = objectMapper.writeValueAsString(ApiResult.getData());

		StringBuffer message = new StringBuffer();
		message.append(System.lineSeparator());
		message.append(String.format("┏━━━━━ [%s.%s] ━━━", className, methodName));
		message.append(System.lineSeparator());
		message.append(String.format("┣ 接口名称：%s", apiName));
		message.append(System.lineSeparator());
		message.append(String.format("┣ 接口方法：%s", apiMethodName));
		message.append(System.lineSeparator());
		message.append(String.format("┣ 接口参数：%s", parameter));
		message.append(System.lineSeparator());
		message.append(String.format("┣ 请求时间：%dms", System.currentTimeMillis() - startTime));
		message.append(System.lineSeparator());
		message.append(String.format("┣ 结果代码：%d", ApiResult.getCode()));
		message.append(System.lineSeparator());
		message.append(String.format("┣ 结果消息：%s", ApiResult.getMessage()));
		message.append(System.lineSeparator());
		message.append(String.format("┣ 结果内容：%s", data));
		message.append(System.lineSeparator());
		message.append(String.format("┗━━━━━ [%s.%s] ━━━", className, methodName));
		message.append(System.lineSeparator());
		return message.toString();
	}

}
