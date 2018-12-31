package com.qbc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.qbc.service.WithdrawalService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WithdrawalServiceTest {

	@Autowired
	private WithdrawalService withdrawalService;

	@Test
	public void testWithdrawal() {
		withdrawalService.withdrawal(null);
	}

}
