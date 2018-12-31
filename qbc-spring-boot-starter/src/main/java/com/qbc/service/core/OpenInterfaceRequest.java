package com.qbc.service.core;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class OpenInterfaceRequest {

	private String api;

	private String method;

	private Map<String, Object> args = new HashMap<>();

}
