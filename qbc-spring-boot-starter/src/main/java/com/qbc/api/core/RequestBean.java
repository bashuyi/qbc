package com.qbc.api.core;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class RequestBean {

	private String api;

	private String method;

	private Map<String, Object> args = new HashMap<>();

}
