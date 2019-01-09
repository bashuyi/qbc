package com.qbc.openinterface;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
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
import com.qbc.exception.UnauthorizedException;
import com.qbc.utils.core.UserUtils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@Controller("${qbc.open-interface.path}")
@ConditionalOnProperty(value = "qbc.open-interface.enable", havingValue = "true")
public class OpenInterfaceController {

	private static final String LOG_PATTEN = String.join(System.lineSeparator(), "", //
			"┏━━━━━开放接口调用开始━━━━━", //
			"┣ 接口名称：{}", //
			"┣ 接口方法：{}", //
			"┣ 接口参数：{}", //
			"┗━━━━━开放接口调用开始━━━━━", "");

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private OpenInterfaceContext openInterfaceContext;

	@Autowired
	private ObjectMapper objectMapper;

	@RequestMapping
	@ResponseBody
	@SneakyThrows
	public Object dispatch(@NotNull @RequestBody(required = false) OpenInterfaceRequest openInterfaceRequest,
			@RequestHeader(required = false) String userId, @RequestHeader(required = false) String username) {
		String openInterfaceServiceName = openInterfaceRequest.getServiceName();
		String openInterfaceMethodName = openInterfaceRequest.getMethodName();
		Map<String, Object> args = ObjectUtils.defaultIfNull(openInterfaceRequest.getArgs(), new HashMap<>());

		UserUtils.setUserId(NumberUtils.toLong(userId));
		UserUtils.setUsername(username);

		if (log.isDebugEnabled()) {
			log.debug(LOG_PATTEN, openInterfaceServiceName, openInterfaceMethodName,
					objectMapper.writeValueAsString(args));
		}

		Object bean = applicationContext.getBean(openInterfaceServiceName);
		Method method = openInterfaceContext.getMethod(openInterfaceServiceName, openInterfaceMethodName);
		Parameter[] parameters = method.getParameters();
		Object[] parameterValues = Arrays.asList(parameters).stream().map(parameter -> {
			if (parameter.getType().equals(OpenInterfaceMapResponse.class)) {
				return OpenInterfaceMapResponse.instance();
			}
			return args.get(parameter.getName());
		}).toArray();

		Object returnValue = ReflectionUtils.invokeMethod(method, bean, parameterValues);

		if (returnValue instanceof OpenInterfaceResponse<?>) {
			return ObjectUtils.defaultIfNull(returnValue, new OpenInterfaceResponse<>());
		}
		return OpenInterfaceResponse.ok(returnValue);
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
	public OpenInterfaceResponse<?> handleBadRequest(Throwable e) {
		return OpenInterfaceResponse.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler({ UnsupportedMediaTypeStatusException.class })
	public OpenInterfaceResponse<?> handleUnsupportedMediaType(Throwable e) {
		return OpenInterfaceResponse.error(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), e.getMessage());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler({ UnauthorizedException.class })
	public OpenInterfaceResponse<?> handleUnauthorized(Throwable e) {
		return OpenInterfaceResponse.error(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Throwable.class)
	public OpenInterfaceResponse<?> handleInternalServerError(Throwable e) {
		// TODO 发邮件等方式通知开发人员
		log.error("未知异常", e);
		return OpenInterfaceResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}

}
