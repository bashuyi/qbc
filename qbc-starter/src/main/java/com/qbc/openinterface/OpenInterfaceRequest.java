package com.qbc.openinterface;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class OpenInterfaceRequest {

	@NotEmpty
	private String beanName;

	@NotEmpty
	private String methodName;

	private Map<String, Object> args = new HashMap<>();

}
