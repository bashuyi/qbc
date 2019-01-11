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
		// 集合初始化时，指定集合初始值大小。如果暂时无法确定初始值大小，请设置为 16（即默认值）。
		openInterfaceMapResponse.data = new HashMap<>(16);
		return openInterfaceMapResponse;
	}

	public void put(String key, Object value) {
		this.data.put(key, value);
	}

}
