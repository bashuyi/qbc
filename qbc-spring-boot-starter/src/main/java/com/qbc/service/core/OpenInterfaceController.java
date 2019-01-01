package com.qbc.service.core;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("${qbc.open-interface.path}")
public class OpenInterfaceController {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private OpenInterfaceContext openInterfaceContext;

	@PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@SneakyThrows
	public Object dispatch(@RequestBody OpenInterfaceRequest requestBean) {
		String openInterfaceBeanName = requestBean.getBeanName();
		String openInterfaceMethodName = requestBean.getMethodName();
		Map<String, Object> args = requestBean.getArgs();
		Object bean = applicationContext.getBean(openInterfaceBeanName);
		Method method = openInterfaceContext.getMethod(openInterfaceBeanName, openInterfaceMethodName);
		Parameter[] parameters = method.getParameters();
		Object[] parameterValues = Arrays.asList(parameters).stream().map(parameter -> args.get(parameter.getName()))
				.toArray();
		Object returnValue = ReflectionUtils.invokeMethod(method, bean, parameterValues);
		if (returnValue instanceof OpenInterfaceResult<?>) {
			return ObjectUtils.defaultIfNull(returnValue, new OpenInterfaceResult<>());
		}
		return new OpenInterfaceResult<>(returnValue);
	}

}
