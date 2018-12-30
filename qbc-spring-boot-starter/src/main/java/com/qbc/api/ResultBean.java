package com.qbc.api;

import java.io.Serializable;

import lombok.Data;

@Data
public class ResultBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int SUCCESS = 0;

	public static final int FAILURE = 1;

	public static final int NO_PERMISSION = 2;

	private int code = SUCCESS;

	private String message = "success";

}
