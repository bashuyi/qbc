package com.qbc.api;

import org.springframework.stereotype.Component;

import com.qbc.api.core.ResultBean;

@Component
public class WithdrawalApi {

	public ResultBean<Object> withdrawal() {
		throw new RuntimeException();
//		return new ResultBean<>();
	}
	
}
