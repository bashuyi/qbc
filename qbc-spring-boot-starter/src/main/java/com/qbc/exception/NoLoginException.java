package com.qbc.exception;

public class NoLoginException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoLoginException() {
		super("未登录异常");
	}

	public NoLoginException(String message) {
		super(message);
	}

	public NoLoginException(Throwable cause) {
		super(cause);
	}

	public NoLoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoLoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
