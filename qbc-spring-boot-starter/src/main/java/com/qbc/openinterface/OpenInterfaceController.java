package com.qbc.openinterface;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller("${qbc.open-interface.path}")
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

	@PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	@SneakyThrows
	@OpenInterfaceLog
	public Object dispatch(@RequestBody OpenInterfaceRequest requestBean) {
		String openInterfaceBeanName = StringUtils.defaultString(requestBean.getBeanName());
		String openInterfaceMethodName = StringUtils.defaultString(requestBean.getMethodName());
		Map<String, Object> args = ObjectUtils.defaultIfNull(requestBean.getArgs(), new HashMap<>());

		if (log.isDebugEnabled()) {
			log.debug(LOG_PATTEN, openInterfaceBeanName, openInterfaceMethodName,
					objectMapper.writeValueAsString(args));
		}

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
