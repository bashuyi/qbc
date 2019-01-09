package com.qbc.openinterface;

import java.util.HashMap;
import java.util.Map;

public class OpenInterfaceMapResponse extends OpenInterfaceResponse<Map<String, Object>> {

	private static final long serialVersionUID = 1L;

	public static OpenInterfaceMapResponse instance() {
		OpenInterfaceMapResponse openInterfaceMapResponse = new OpenInterfaceMapResponse();
		openInterfaceMapResponse.data = new HashMap<>();
		return openInterfaceMapResponse;
	}

	public void put(String key, Object value) {
		this.data.put(key, value);
	}

}
