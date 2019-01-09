package com.qbc.openinterface;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
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
	@OpenInterfaceLog
	public Object dispatch(@NotNull @RequestBody(required = false) OpenInterfaceRequest openInterfaceRequest,
			@RequestHeader(required = false) String userId, @RequestHeader(required = false) String username) {
		try {
			String openInterfaceBeanName = openInterfaceRequest.getBeanName();
			String openInterfaceMethodName = openInterfaceRequest.getMethodName();
			Map<String, Object> args = ObjectUtils.defaultIfNull(openInterfaceRequest.getArgs(), new HashMap<>());

			UserUtils.setUserId(NumberUtils.toLong(userId));
			UserUtils.setUsername(username);

			if (log.isDebugEnabled()) {
				log.debug(LOG_PATTEN, openInterfaceBeanName, openInterfaceMethodName,
						objectMapper.writeValueAsString(args));
			}

			Object bean = applicationContext.getBean(openInterfaceBeanName);
			Method method = openInterfaceContext.getMethod(openInterfaceBeanName, openInterfaceMethodName);
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
		} finally {
			UserUtils.removeAll();
		}
	}

}
