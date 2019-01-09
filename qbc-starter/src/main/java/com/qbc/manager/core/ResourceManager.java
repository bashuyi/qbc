package com.qbc.manager.core;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.qbc.manager.core.ResourceDTO.ArgumentDTO;
import com.qbc.manager.core.ResourceDTO.MethodDTO;
import com.qbc.manager.core.ResourceDTO.ServiceDTO;
import com.qbc.openinterface.OpenInterface;
import com.qbc.openinterface.OpenInterfaceArgument;
import com.qbc.openinterface.OpenInterfaceMethod;

import lombok.Getter;

@Component
public class ResourceManager implements ApplicationRunner {

	@Autowired
	private ResourceProperties resourceProperties;

	@Autowired
	private ApplicationContext applicationContext;

	@Getter
	private ResourceDTO resourceDTO;

	public Method getMethod(String serviceName, String methodName) {
		return Optional.ofNullable(resourceDTO).map(ResourceDTO::getServiceMap)
				.map(serviceMap -> serviceMap.get(serviceName)).map(ServiceDTO::getMethodMap)
				.map(methodMap -> methodMap.get(methodName)).map(MethodDTO::getMethod)
				.orElseThrow(() -> new IllegalArgumentException(
						String.format("No method named '%s' available", methodName)));
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		resourceDTO = new ResourceDTO();
		resourceDTO.setApplicationName(resourceProperties.getName());
		resourceDTO.setApplicationDescription(resourceProperties.getDescription());

		Map<String, Object> beans = applicationContext.getBeansWithAnnotation(OpenInterface.class);
		beans.forEach((beanName, bean) -> {
			Class<?> classType = AopUtils.getTargetClass(bean);
			OpenInterface openInterface = classType.getAnnotation(OpenInterface.class);
			String serviceName = StringUtils.defaultIfEmpty(openInterface.value(), beanName);

			ServiceDTO serviceDTO = new ServiceDTO();
			serviceDTO.setServiceName(serviceName);
			serviceDTO.setServiceDescription(openInterface.description());
			resourceDTO.getServiceMap().put(serviceName, serviceDTO);

			List<Method> methods = MethodUtils.getMethodsListWithAnnotation(classType, OpenInterfaceMethod.class);
			methods.stream().forEach(method -> {
				OpenInterfaceMethod openInterfaceMethod = method.getAnnotation(OpenInterfaceMethod.class);
				String methodName = StringUtils.defaultIfEmpty(openInterfaceMethod.value(), method.getName());

				MethodDTO methodDTO = new MethodDTO();
				methodDTO.setMethodName(methodName);
				methodDTO.setMethodDescription(openInterfaceMethod.description());
				methodDTO.setMethod(method);
				serviceDTO.getMethodMap().put(methodName, methodDTO);

				Parameter[] parameters = method.getParameters();
				List<ArgumentDTO> arguments = Arrays.asList(parameters).stream().map(parameter -> {
					Optional<OpenInterfaceArgument> openInterfaceArgument = Optional
							.ofNullable(parameter.getAnnotation(OpenInterfaceArgument.class));
					String argumentName = StringUtils.defaultIfEmpty(
							openInterfaceArgument.map(OpenInterfaceArgument::value).orElse(""), parameter.getName());
					boolean required = parameter.isAnnotationPresent(NotNull.class)
							|| parameter.isAnnotationPresent(NotEmpty.class);

					ArgumentDTO argumentDTO = new ArgumentDTO();
					argumentDTO.setArgumentName(argumentName);
					argumentDTO.setArgumentDescription(
							openInterfaceArgument.map(OpenInterfaceArgument::description).orElse(""));
					argumentDTO.setArgumentType(parameter.getType().getName());
					argumentDTO.setRequired(required);

					return argumentDTO;
				}).collect(Collectors.toList());
				methodDTO.setArgs(arguments);
			});
		});
	}

}
