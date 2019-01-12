package com.qbc.api;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qbc.constant.LogPatten;
import com.qbc.exception.UnauthorizedException;
import com.qbc.utils.core.UserUtils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 开放接口层API的统一外部访问入口。记录了请求的内容，并统一处理了异常。
 *
 * @author Ma
 */
@Slf4j
@Validated
@Controller("${qbc.api.path}")
@ConditionalOnProperty(value = "qbc.api.enable", havingValue = "true")
public class ApiController {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ApiContext apiContext;

	@Autowired
	private ObjectMapper objectMapper;

	@RequestMapping
	@ResponseBody
	@SneakyThrows
	public Object dispatch(@NotNull @RequestBody(required = false) ApiRequest apiRequest,
			@RequestHeader(required = false) String userId, @RequestHeader(required = false) String username) {
		String apiName = apiRequest.getApiName();
		String operationName = apiRequest.getApiOperationName();
		Map<String, Object> apiParams = apiRequest.getApiParams();

		// 设置用户信息到请求的上下文，用于日志和数据库等
		UserUtils.setUserId(NumberUtils.toLong(userId));
		UserUtils.setUsername(username);

		// 记录了请求的内容
		if (log.isDebugEnabled()) {
			log.debug(LogPatten.API_START, apiName, operationName, objectMapper.writeValueAsString(apiParams));
		}

		// 获得Bean
		Object bean = applicationContext.getBean(apiName);

		// 从上下文缓存中获得服务方法
		Method method = apiContext.getMethod(apiName, operationName);

		// 获得方法参数
		Parameter[] parameters = method.getParameters();
		Object[] parameterValues = Arrays.asList(parameters).stream().map(parameter -> {
			if (parameter.getType().equals(ApiMapResponse.class)) {
				return ApiMapResponse.instance();
			}
			return apiParams.get(parameter.getName());
		}).toArray();

		// 反射执行服务的方法
		Object returnValue = ReflectionUtils.invokeMethod(method, bean, parameterValues);

		// 将返回值封装到响应中
		if (returnValue instanceof ApiResponse<?>) {
			return ObjectUtils.defaultIfNull(returnValue, new ApiResponse<>());
		}
		return ApiResponse.ok(returnValue);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({
			// Bean不存在
			NoSuchBeanDefinitionException.class,
			// Bean的方法不匹配或者断言异常
			IllegalArgumentException.class,
			// 参数验证错误
			ConstraintViolationException.class })
	public ApiResponse<?> handleBadRequest(Throwable e) {
		return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler({ UnsupportedMediaTypeStatusException.class })
	public ApiResponse<?> handleUnsupportedMediaType(Throwable e) {
		return ApiResponse.error(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), e.getMessage());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler({ UnauthorizedException.class })
	public ApiResponse<?> handleUnauthorized(Throwable e) {
		return ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Throwable.class)
	public ApiResponse<?> handleInternalServerError(Throwable e) {
		// TODO 发邮件等方式通知开发人员
		log.error("未知异常", e);
		return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}

}
