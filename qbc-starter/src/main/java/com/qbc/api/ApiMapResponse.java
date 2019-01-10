package com.qbc.api;

import java.util.HashMap;
import java.util.Map;

/**
 * API响应，响应内容为Map类型。一般响应内容为多个时使用
 *
 * @author Ma
 */
public class ApiMapResponse extends ApiResponse<Map<String, Object>> {

	private static final long serialVersionUID = 1L;

	public static ApiMapResponse instance() {
		ApiMapResponse openInterfaceMapResponse = new ApiMapResponse();
		openInterfaceMapResponse.data = new HashMap<>();
		return openInterfaceMapResponse;
	}

	public void put(String key, Object value) {
		this.data.put(key, value);
	}

}
