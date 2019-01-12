package com.qbc.service;

import javax.validation.constraints.NotEmpty;

import org.springframework.validation.annotation.Validated;

import com.qbc.api.annotation.Api;
import com.qbc.api.annotation.ApiOperation;

@Api
@Validated
public class WithdrawalService {

	@ApiOperation
	public void withdrawal(@NotEmpty String id) {
	}

}
