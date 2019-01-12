package com.qbc.manager.core;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.qbc.api.ApiMapResponse;
import com.qbc.api.annotation.Api;
import com.qbc.api.annotation.ApiOperation;

import lombok.SneakyThrows;

/**
 * API处理，启动应用时获取所有API信息，提供执行API的方法
 *
 * @author Ma
 */
@Component
public class ApiManageer implements ApplicationRunner {

	@Autowired
	private ApplicationContext applicationContext;

	private Table<String, String, Method> methodTable = HashBasedTable.create();

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 扫描所有API
		Map<String, Object> beans = applicationContext.getBeansWithAnnotation(Api.class);
		beans.forEach((beanName, bean) -> {
			// 获得API真实的服务类，而不是代理类
			Class<?> classType = AopUtils.getTargetClass(bean);

			// 获得API名，如果没有指定就使用Bean名称
			Api api = classType.getAnnotation(Api.class);
			String apiName = StringUtils.defaultIfEmpty(api.name(), beanName);

			// 扫描所以API操作的方法
			List<Method> methods = MethodUtils.getMethodsListWithAnnotation(classType, ApiOperation.class);
			methods.stream().forEach(method -> {
				// 获得API方法名，如果没有指定就使用方法名
				ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
				String operationName = StringUtils.defaultIfEmpty(apiOperation.name(), method.getName());
				methodTable.put(apiName, operationName, method);
			});
		});
	}

	@SneakyThrows
	public Object invoke(String apiName, String operationName, Map<String, Object> params) {
		// 获得方法
		Method method = methodTable.get(apiName, operationName);
		Assert.notNull(method, String.format("No method named '%s' available", operationName));

		// 获得Bean
		Object bean = applicationContext.getBean(apiName);

		// 获得方法参数
		Parameter[] parameters = method.getParameters();
		Object[] args = Arrays.asList(parameters).stream().map(parameter -> {
			if (parameter.getType().equals(ApiMapResponse.class)) {
				return ApiMapResponse.instance();
			}
			return params.get(parameter.getName());
		}).toArray();

		return ReflectionUtils.invokeMethod(method, bean, args);
	}

}
