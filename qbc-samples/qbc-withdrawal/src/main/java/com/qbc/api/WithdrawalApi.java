package com.qbc.api;

import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.qbc.api.core.ResultBean;

@Component
@Validated
public class WithdrawalApi {

	public ResultBean<Object> withdrawal(@NotEmpty String id) {
		return new ResultBean<>();
	}

}
