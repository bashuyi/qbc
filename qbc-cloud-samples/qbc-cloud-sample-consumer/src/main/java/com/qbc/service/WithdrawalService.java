package com.qbc.service;

import javax.validation.constraints.NotEmpty;

import org.springframework.validation.annotation.Validated;

import com.qbc.api.Api;
import com.qbc.api.ApiOperation;

@Api
@Validated
public class WithdrawalService {

	@ApiOperation
	public void withdrawal(@NotEmpty String id) {
	}

}
