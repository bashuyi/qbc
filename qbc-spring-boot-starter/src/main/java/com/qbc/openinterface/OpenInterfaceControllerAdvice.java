package com.qbc.openinterface;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(assignableTypes = OpenInterfaceController.class)
public class OpenInterfaceControllerAdvice {

	@ResponseBody
	@OpenInterfaceLog
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ NoSuchBeanDefinitionException.class, IllegalArgumentException.class,
			ConstraintViolationException.class })
	public OpenInterfaceResponse<?> handleBadRequest(Throwable e) {
		return new OpenInterfaceResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}

	@ResponseBody
	@OpenInterfaceLog
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Throwable.class)
	public OpenInterfaceResponse<?> handleInternalServerError(Throwable e) {
		// TODO 发邮件等方式通知开发人员
		log.error("未知异常", e);
		return new OpenInterfaceResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}

}
