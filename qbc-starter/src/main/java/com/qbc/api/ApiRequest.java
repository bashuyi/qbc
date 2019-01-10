package com.qbc.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API请求
 *
 * @author Ma
 */
@Data
@NoArgsConstructor
public class ApiRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * API名
	 */
	@NotEmpty
	private String apiName;

	/**
	 * API操作名
	 */
	@NotEmpty
	private String apiOperationName;

	/**
	 * API参数
	 */
	private Map<String, Object> apiParams = new HashMap<>();

	/**
	 * 设置API参数
	 * 
	 * @param key   参数键
	 * @param value 参数值
	 */
	public void put(String key, String value) {
		this.apiParams.put(key, value);
	}

	/**
	 * API请求
	 * 
	 * @param apiName          API名
	 * @param apiOperationName API操作名
	 */
	public ApiRequest(@NotEmpty String apiName, @NotEmpty String apiOperationName) {
		this.apiName = apiName;
		this.apiOperationName = apiOperationName;
	}

}
