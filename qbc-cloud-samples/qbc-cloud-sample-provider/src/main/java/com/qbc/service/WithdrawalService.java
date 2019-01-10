package com.qbc.service;

import javax.validation.constraints.NotEmpty;

import org.springframework.validation.annotation.Validated;

import com.qbc.api.Api;
import com.qbc.api.ApiMapResponse;
import com.qbc.api.ApiOperation;

@Api
@Validated
public class WithdrawalService {

	@ApiOperation
	public ApiMapResponse withdrawal(ApiMapResponse openInterfaceMapResponse, @NotEmpty String id) {
		openInterfaceMapResponse.setCode(1);
		openInterfaceMapResponse.setMessage("業務エラー");
		openInterfaceMapResponse.put("id", id);
		return openInterfaceMapResponse;
	}

}
