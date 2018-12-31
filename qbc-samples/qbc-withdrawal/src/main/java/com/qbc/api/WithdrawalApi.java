package com.qbc.api;

import javax.validation.constraints.NotEmpty;

import org.springframework.validation.annotation.Validated;

import com.qbc.api.core.API;

@API
@Validated
public class WithdrawalApi {

	public void withdrawal(@NotEmpty String id) {
	}

}
