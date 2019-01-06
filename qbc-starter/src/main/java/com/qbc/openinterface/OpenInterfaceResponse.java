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

	public static final int HYSTRIX = -1;

	public static final int OK = 200;

	private Integer code;

	private String message;

	private T data;

	public static OpenInterfaceResponse<Void> ok() {
		OpenInterfaceResponse<Void> openInterfaceResponse = new OpenInterfaceResponse<>();
		openInterfaceResponse.code = OK;
		openInterfaceResponse.message = "";
		return openInterfaceResponse;
	}

	public static <T> OpenInterfaceResponse<T> ok(T data) {
		OpenInterfaceResponse<T> openInterfaceResponse = new OpenInterfaceResponse<>();
		openInterfaceResponse.code = OK;
		openInterfaceResponse.message = "";
		openInterfaceResponse.data = data;
		return openInterfaceResponse;
	}

	public static <T> OpenInterfaceResponse<T> error(int code, String message) {
		OpenInterfaceResponse<T> openInterfaceResponse = new OpenInterfaceResponse<>();
		openInterfaceResponse.code = code;
		openInterfaceResponse.message = message;
		return openInterfaceResponse;
	}

	public static <T> OpenInterfaceResponse<T> hystrix() {
		OpenInterfaceResponse<T> openInterfaceResponse = new OpenInterfaceResponse<>();
		openInterfaceResponse.code = HYSTRIX;
		openInterfaceResponse.message = "";
		return openInterfaceResponse;
	}

}
