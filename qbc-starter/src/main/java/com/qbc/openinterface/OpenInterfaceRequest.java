package com.qbc.openinterface;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class OpenInterfaceRequest {

	private String beanName;

	private String methodName;

	private Map<String, Object> args = new HashMap<>();

}
