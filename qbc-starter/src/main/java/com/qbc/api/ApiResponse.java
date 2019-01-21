package com.qbc.api;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * API响应<br>
 * 0：正常<br>
 * 1：请求内容错误。请求内容和接口规范不符，需要按照响应信息和接口规范修改后重新请求。<br>
 * 2：认证错误。没有授权或者没有通过认证，需要获得授权和通过认证才能访问。 <br>
 * 3：熔断。请求的服务暂时无法访问，需要等待服务恢复后重试。<br>
 * 4：内部错误。发生预料外的异常，需要联系技术担当修复。<br>
 *
 * @author Ma
 * @param <T> 结果数据的类型
 */
@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int OK = 0;

	public static final int BAD_REQUEST = 1;

	public static final int UNAUTHORIZED = 2;

	public static final int ERROR = 3;

	public static final int HYSTRIX = 4;

	/**
	 * 响应编码
	 */
	protected int code = OK;

	/**
	 * 响应信息
	 */
	protected String message = "ok";

	/**
	 * 响应内容
	 */
	protected T data;

	/**
	 * 自定义响应结果
	 * 
	 * @param code    响应编码
	 * @param message 响应信息
	 */
	public static <T> ApiResponse<T> response(int code, String message) {
		ApiResponse<T> apiResponse = new ApiResponse<>();
		apiResponse.code = code;
		apiResponse.message = StringUtils.defaultString(message);
		return apiResponse;
	}

	/**
	 * 正常响应并含有响应内容
	 */
	public static <T> ApiResponse<T> ok(T data) {
		ApiResponse<T> apiResponse = new ApiResponse<>();
		apiResponse.setData(data);
		return apiResponse;
	}

	/**
	 * 正常响应
	 */
	public static ApiResponse<Void> ok() {
		return ok(null);
	}

	/**
	 * 请求内容错误响应
	 * 
	 * @param message 错误信息
	 */
	public static <T> ApiResponse<T> badRequest(String message) {
		return response(BAD_REQUEST, StringUtils.defaultIfEmpty(message, "bad request"));
	}

	/**
	 * 请求内容错误响应
	 * 
	 * @param e 异常
	 */
	public static <T> ApiResponse<T> badRequest(Throwable e) {
		return badRequest(e.getMessage());
	}

	/**
	 * 请求内容错误响应
	 */
	public static <T> ApiResponse<T> badRequest() {
		return badRequest("");
	}

	/**
	 * 认证错误响应
	 * 
	 * @param message 错误信息
	 */
	public static <T> ApiResponse<T> unauthorized(String message) {
		return response(UNAUTHORIZED, StringUtils.defaultIfEmpty(message, "unauthorized"));
	}

	/**
	 * 认证错误响应
	 * 
	 * @param e 异常
	 */
	public static <T> ApiResponse<T> unauthorized(Throwable e) {
		return unauthorized(e.getMessage());
	}

	/**
	 * 认证错误响应
	 */
	public static <T> ApiResponse<T> unauthorized() {
		return unauthorized("");
	}

	/**
	 * 熔断异常响应
	 * 
	 * @param message 错误信息
	 */
	public static <T> ApiResponse<T> hystrix(String message) {
		return response(HYSTRIX, StringUtils.defaultIfEmpty(message, "hystrix"));
	}

	/**
	 * 熔断异常响应
	 */
	public static <T> ApiResponse<T> hystrix() {
		return hystrix(null);
	}

	/**
	 * 内部错误响应
	 * 
	 * @param message 错误信息
	 */
	public static <T> ApiResponse<T> error(Throwable e) {
		return response(ERROR, StringUtils.defaultString(e.getMessage()));
	}

	/**
	 * 是否正常响应
	 */
	@JsonIgnore
	public boolean isOk() {
		return code == OK;
	}

}
