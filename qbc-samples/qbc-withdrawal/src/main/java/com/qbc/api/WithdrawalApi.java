package com.qbc.api;

import javax.validation.constraints.NotEmpty;

import org.springframework.validation.annotation.Validated;

import com.qbc.service.core.OpenInterface;

@OpenInterface
@Validated
public class WithdrawalApi {

	public void withdrawal(@NotEmpty String id) {
	}

}
