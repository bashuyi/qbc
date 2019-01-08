package com.qbc.openinterface;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.qbc.exception.UnauthorizedException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(assignableTypes = OpenInterfaceController.class)
public class OpenInterfaceControllerAdvice {

	@ResponseBody
	@OpenInterfaceLog
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({
			// Bean不存在
			NoSuchBeanDefinitionException.class,
			// Bean的方法不匹配或者断言异常
			IllegalArgumentException.class,
			// 参数验证错误
			ConstraintViolationException.class })
	public OpenInterfaceResponse<?> handleBadRequest(Throwable e) {
		return OpenInterfaceResponse.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}

//	@ResponseBody
//	@OpenInterfaceLog
//	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
//	@ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
//	public OpenInterfaceResponse<?> handleUnsupportedMediaType(Throwable e) {
//		return OpenInterfaceResponse.error(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), e.getMessage());
//	}

	@ResponseBody
	@OpenInterfaceLog
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler({ UnauthorizedException.class })
	public OpenInterfaceResponse<?> handleUnauthorized(Throwable e) {
		return OpenInterfaceResponse.error(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
	}

	@ResponseBody
	@OpenInterfaceLog
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Throwable.class)
	public OpenInterfaceResponse<?> handleInternalServerError(Throwable e) {
		// TODO 发邮件等方式通知开发人员
		log.error("未知异常", e);
		return OpenInterfaceResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}

}
