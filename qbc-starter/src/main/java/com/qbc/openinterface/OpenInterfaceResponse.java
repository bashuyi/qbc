package com.qbc.openinterface;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 开放接口层的统一返回结果
 *
 * @author Ma
 * @param <T> 结果数据的类型
 */
@Getter
@Setter
@NoArgsConstructor
public class OpenInterfaceResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int HYSTRIX = -1;

	public static final int OK = 200;

	protected int code = OK;

	protected String message = "";

	protected T data;

	public static OpenInterfaceResponse<Void> ok() {
		OpenInterfaceResponse<Void> openInterfaceResponse = new OpenInterfaceResponse<>();
		return openInterfaceResponse;
	}

	public static <T> OpenInterfaceResponse<T> ok(T data) {
		OpenInterfaceResponse<T> openInterfaceResponse = new OpenInterfaceResponse<>();
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

	@JsonIgnore
	public boolean isOk() {
		return code == OK;
	}

}
