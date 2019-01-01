package com.qbc.service;

import javax.validation.constraints.NotEmpty;

import org.springframework.validation.annotation.Validated;

import com.qbc.service.core.OpenInterface;
import com.qbc.service.core.OpenInterfaceMethod;

@OpenInterface
@Validated
public class WithdrawalService {

	@OpenInterfaceMethod
	public void withdrawal(@NotEmpty String id) {
	}

}
