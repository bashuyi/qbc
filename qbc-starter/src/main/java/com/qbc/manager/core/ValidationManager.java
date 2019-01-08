package com.qbc.manager.core;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
public class ValidationManager {

	public <T> T notNull(@NotNull T value) {
		return value;
	}

	public String notEmpty(@NotEmpty String value) {
		return value;
	}

	public String notBlank(@NotBlank String value) {
		return value;
	}

	public boolean assertTrue(@AssertTrue boolean value) {
		return value;
	}

}
