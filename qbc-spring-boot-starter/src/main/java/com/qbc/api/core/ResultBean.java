package com.qbc.api.core;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResultBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int SUCCESS = 0;

	public static final int INVALID = 1;

	public static final int NO_LOGIN = 2;

	public static final int NO_PERMISSION = 3;

	public static final int FAILURE = 4;

	private int code = SUCCESS;

	private String message = "success";

	private T data;

	public ResultBean(T data) {
		super();
		this.data = data;
	}

	public ResultBean(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public ResultBean(Throwable e) {
		super();
		this.code = FAILURE;
		this.message = e.getMessage();
	}

	public boolean isSuccess() {
		return code == SUCCESS;
	}

}
