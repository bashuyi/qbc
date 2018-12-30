package com.qbc.api;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int SUCCESS = 0;

	public static final int FAILURE = 1;

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

}
