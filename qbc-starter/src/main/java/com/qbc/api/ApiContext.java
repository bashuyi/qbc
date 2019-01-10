package com.qbc.api;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * API上下文，应用启动时缓存API，提高反射效率。
 *
 * @author Ma
 */
@Component
public class ApiContext implements ApplicationRunner {

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
				String apiOperationName = StringUtils.defaultIfEmpty(apiOperation.name(), method.getName());
				methodTable.put(apiName, apiOperationName, method);
				
				// TODO 获得方法的索引，用于提高反射效率
				MethodAccess access = MethodAccess.get(classType);
				int index = access.getIndex(method.getName());
			});
		});
	}

	public Method getMethod(String openInterfaceBeanName, String openInterfaceMethodName) {
		return Optional.ofNullable(methodTable.get(openInterfaceBeanName, openInterfaceMethodName))
				.orElseThrow(() -> new IllegalArgumentException(
						String.format("No method named '%s' available", openInterfaceMethodName)));
	}

}
