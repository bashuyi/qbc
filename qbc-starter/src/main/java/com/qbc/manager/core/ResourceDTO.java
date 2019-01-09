package com.qbc.manager.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ResourceDTO {

	private String applicationName;

	private String applicationDescription;

	private Map<String, ServiceDTO> serviceMap = new HashMap<>();

	@Data
	public static class ServiceDTO {

		private String serviceName;

		private String serviceDescription;

		private Map<String, MethodDTO> methodMap = new HashMap<>();

	}

	@Data
	public static class MethodDTO {

		private String methodName;

		private String methodDescription;

		private List<ArgumentDTO> args = new ArrayList<>();

		@JsonIgnore
		private Method method;

	}

	@Data
	public static class ArgumentDTO {

		private String argumentName;

		private String argumentDescription;

		private String argumentType;

		private boolean required;

	}

}
