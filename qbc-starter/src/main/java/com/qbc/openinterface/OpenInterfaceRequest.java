package com.qbc.openinterface;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpenInterfaceRequest {

	@NotEmpty
	private String serviceName;

	@NotEmpty
	private String methodName;

	private Map<String, Object> args = new HashMap<>();

	public void put(String key, String value) {
		this.args.put(key, value);
	}

	public OpenInterfaceRequest(String serviceName, String methodName) {
		this.serviceName = serviceName;
		this.methodName = methodName;
	}

}
