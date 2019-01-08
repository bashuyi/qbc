package com.qbc.service;

import javax.validation.constraints.NotEmpty;

import org.springframework.validation.annotation.Validated;

import com.qbc.openinterface.OpenInterface;
import com.qbc.openinterface.OpenInterfaceMapResponse;
import com.qbc.openinterface.OpenInterfaceMethod;

@OpenInterface
@Validated
public class WithdrawalService {

	@OpenInterfaceMethod
	public OpenInterfaceMapResponse withdrawal(OpenInterfaceMapResponse openInterfaceMapResponse, @NotEmpty String id) {
		openInterfaceMapResponse.setCode(1);
		openInterfaceMapResponse.setMessage("業務エラー");
		openInterfaceMapResponse.put("id", id);
		return openInterfaceMapResponse;
	}

}
