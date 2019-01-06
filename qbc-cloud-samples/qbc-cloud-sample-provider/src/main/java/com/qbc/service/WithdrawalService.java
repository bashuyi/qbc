package com.qbc.service;

import javax.validation.constraints.NotEmpty;

import org.springframework.validation.annotation.Validated;

import com.qbc.openinterface.OpenInterface;
import com.qbc.openinterface.OpenInterfaceMethod;

@OpenInterface
@Validated
public class WithdrawalService {

	@OpenInterfaceMethod
	public void withdrawal(@NotEmpty String id) {
	}

}
