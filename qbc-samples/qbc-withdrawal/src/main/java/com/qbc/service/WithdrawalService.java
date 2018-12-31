package com.qbc.service;

import javax.validation.constraints.NotEmpty;

import org.springframework.validation.annotation.Validated;

import com.qbc.service.core.OpenInterface;

@OpenInterface
@Validated
public class WithdrawalService {

	public void withdrawal(@NotEmpty String id) {
	}

}
