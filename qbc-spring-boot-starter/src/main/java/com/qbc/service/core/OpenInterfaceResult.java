package com.qbc.service.core;

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
public class OpenInterfaceResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int SUCCESS = 0;

	public static final int INVALID = 1;

	public static final int NO_LOGIN = 2;

	public static final int NO_PERMISSION = 3;

	public static final int FAILURE = 4;

	private int code = SUCCESS;

	private String message = "success";

	private T data;

	public OpenInterfaceResult(T data) {
		super();
		this.data = data;
	}

	public OpenInterfaceResult(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public boolean isSuccess() {
		return code == SUCCESS;
	}

}
