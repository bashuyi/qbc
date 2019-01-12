package com.qbc.manager.core;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
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
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.qbc.api.ApiMapResponse;
import com.qbc.api.annotation.Api;
import com.qbc.api.annotation.ApiOperation;
import com.qbc.api.annotation.ApiParam;
import com.qbc.dto.core.ApiDTO;
import com.qbc.dto.core.ApiOperationDTO;
import com.qbc.dto.core.ApiParamDTO;
import com.qbc.dto.core.ApplicationDTO;

import lombok.Getter;
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

	@Autowired
	private ApplicationProperties applicationProperties;

	private Table<String, String, Method> methodTable = HashBasedTable.create();

	@Getter
	private ApplicationDTO applicationDTO;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		List<ApiDTO> apiList = new ArrayList<>();

		// 扫描所有API
		Map<String, Object> beans = applicationContext.getBeansWithAnnotation(Api.class);
		beans.forEach((beanName, bean) -> {
			// 获得API真实的服务类，而不是代理类
			Class<?> classType = AopUtils.getTargetClass(bean);

			// 获得API名，如果没有指定就使用Bean名称
			Api api = classType.getAnnotation(Api.class);
			String apiName = StringUtils.defaultIfEmpty(api.name(), beanName);

			// 扫描所以API操作的方法
			List<ApiOperationDTO> apiOperationList = new ArrayList<>();
			List<Method> methods = MethodUtils.getMethodsListWithAnnotation(classType, ApiOperation.class);
			methods.stream().forEach(method -> {
				// 获得API方法名，如果没有指定就使用方法名
				ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
				String operationName = StringUtils.defaultIfEmpty(apiOperation.name(), method.getName());
				methodTable.put(apiName, operationName, method);

				// 获得API的所有参数信息
				Parameter[] parameters = method.getParameters();
				List<ApiParamDTO> apiParamList = Arrays.asList(parameters).stream().filter(this::isNotApiMapResponse)
						.map(parameter -> {
							Optional<ApiParam> apiParam = Optional.ofNullable(parameter.getAnnotation(ApiParam.class));
							String paramName = StringUtils.defaultIfEmpty(apiParam.map(ApiParam::name).orElse(""),
									parameter.getName());
							boolean required = parameter.isAnnotationPresent(NotNull.class)
									|| parameter.isAnnotationPresent(NotEmpty.class);

							// 获得API参数信息
							ApiParamDTO apiParamDTO = new ApiParamDTO();
							apiParamDTO.setName(paramName);
							apiParamDTO.setDisplayName(apiParam.map(ApiParam::displayName).orElse(""));
							apiParamDTO.setDescription(apiParam.map(ApiParam::description).orElse(""));
							apiParamDTO.setTypeName(parameter.getType().getName());
							apiParamDTO.setRequired(required);
							return apiParamDTO;
						}).collect(Collectors.toList());

				// 获得API操作信息
				ApiOperationDTO apiOperationDTO = new ApiOperationDTO();
				apiOperationDTO.setName(operationName);
				apiOperationDTO.setDisplayName(apiOperation.displayName());
				apiOperationDTO.setDescription(apiOperation.description());
				apiOperationDTO.setApiParamList(apiParamList);
				apiOperationList.add(apiOperationDTO);
			});

			// 获得API信息
			ApiDTO apiDTO = new ApiDTO();
			apiDTO.setName(apiName);
			apiDTO.setDisplayName(api.displayName());
			apiDTO.setDescription(api.description());
			apiDTO.setApiOperationList(apiOperationList);
			apiList.add(apiDTO);
		});

		// 获得应用信息
		applicationDTO = new ApplicationDTO();
		applicationDTO.setName(StringUtils.defaultString(applicationProperties.getName()));
		applicationDTO.setDisplayName(StringUtils.defaultString(applicationProperties.getDisplayName()));
		applicationDTO.setDescription(StringUtils.defaultString(applicationProperties.getDescription()));
		applicationDTO.setApiList(apiList);
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
			Optional<ApiParam> apiParam = Optional.ofNullable(parameter.getAnnotation(ApiParam.class));
			String paramName = StringUtils.defaultIfEmpty(apiParam.map(ApiParam::name).orElse(""), parameter.getName());
			return params.get(paramName);
		}).toArray();

		return ReflectionUtils.invokeMethod(method, bean, args);
	}

	private boolean isNotApiMapResponse(Parameter parameter) {
		return parameter.getType().equals(ApiMapResponse.class) == false;
	}

}
