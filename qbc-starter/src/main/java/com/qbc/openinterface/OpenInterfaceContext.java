package com.qbc.openinterface;

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

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

@Component
public class OpenInterfaceContext implements ApplicationRunner {

	@Autowired
	private ApplicationContext applicationContext;

	private Table<String, String, Method> methodTable = HashBasedTable.create();

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Map<String, Object> beans = applicationContext.getBeansWithAnnotation(OpenInterface.class);
		beans.forEach((beanName, bean) -> {
			Class<?> classType = AopUtils.getTargetClass(bean);
			OpenInterface openInterface = classType.getAnnotation(OpenInterface.class);
			String openInterfaceBeanName = StringUtils.defaultIfEmpty(openInterface.value(), beanName);
			List<Method> methods = MethodUtils.getMethodsListWithAnnotation(classType, OpenInterfaceMethod.class);
			methods.stream().forEach(method -> {
				OpenInterfaceMethod openInterfaceMethod = method.getAnnotation(OpenInterfaceMethod.class);
				String openInterfaceMethodName = StringUtils.defaultIfEmpty(openInterfaceMethod.value(),
						method.getName());
				methodTable.put(openInterfaceBeanName, openInterfaceMethodName, method);
			});
		});
	}

	public Method getMethod(String openInterfaceBeanName, String openInterfaceMethodName) {
		return Optional.ofNullable(methodTable.get(openInterfaceBeanName, openInterfaceMethodName))
				.orElseThrow(() -> new IllegalArgumentException(
						String.format("No method named '%s' available", openInterfaceMethodName)));
	}

}
