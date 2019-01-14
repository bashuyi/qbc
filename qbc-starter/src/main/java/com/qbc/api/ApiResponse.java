package com.qbc.api;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * API响应
 *
 * @author Ma
 * @param <T> 结果数据的类型
 */
@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int HYSTRIX = -1;

	public static final int OK = 200;

	/**
	 * 响应编码
	 */
	protected int code = OK;

	/**
	 * 响应信息
	 */
	protected String message = "";

	/**
	 * 响应内容
	 */
	protected T data;

	/**
	 * 正常响应
	 */
	public static ApiResponse<Void> ok() {
		ApiResponse<Void> apiResponse = new ApiResponse<>();
		return apiResponse;
	}

	/**
	 * 正常响应并含有响应内容
	 */
	public static <T> ApiResponse<T> ok(T data) {
		ApiResponse<T> apiResponse = new ApiResponse<>();
		apiResponse.data = data;
		return apiResponse;
	}

	/**
	 * 异常响应
	 * 
	 * @param code    错误编码
	 * @param message 错误信息
	 */
	public static <T> ApiResponse<T> error(int code, String message) {
		ApiResponse<T> apiResponse = new ApiResponse<>();
		apiResponse.code = code;
		apiResponse.message = StringUtils.defaultString(message);
		return apiResponse;
	}

	/**
	 * 异常响应
	 * 
	 * @param httpStatus HTTP状态码
	 * @param message    错误信息
	 */
	public static <T> ApiResponse<T> error(HttpStatus httpStatus, String message) {
		return error(httpStatus.value(), message);
	}

	/**
	 * 异常响应
	 * 
	 * @param httpStatus HTTP状态码
	 * @param e          异常信息
	 */
	public static <T> ApiResponse<T> error(HttpStatus httpStatus, Throwable e) {
		return error(httpStatus.value(), e.getMessage());
	}

	/**
	 * 异常响应
	 * 
	 * @param httpStatus HTTP状态码
	 */
	public static <T> ApiResponse<T> error(HttpStatus httpStatus) {
		return error(httpStatus.value(), httpStatus.getReasonPhrase());
	}

	/**
	 * 熔断异常响应，微服务熔断时发生。
	 */
	public static <T> ApiResponse<T> hystrix() {
		return error(HYSTRIX, "");
	}

	/**
	 * 是否正常响应
	 */
	@JsonIgnore
	public boolean isOk() {
		return code == OK;
	}

}
