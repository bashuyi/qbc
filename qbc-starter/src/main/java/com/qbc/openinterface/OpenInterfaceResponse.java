package com.qbc.openinterface;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 开放接口层的统一返回结果
 *
 * @author Ma
 * @param <T> 结果数据的类型
 */
@Getter
@NoArgsConstructor
public class OpenInterfaceResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int OK = 200;

	private int code = OK;

	private String message = "OK";

	private T data;

	public OpenInterfaceResponse(T data) {
		super();
		this.data = data;
	}

	public OpenInterfaceResponse(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

}
