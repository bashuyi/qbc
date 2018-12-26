package com.qbc;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 项目启动类的测试类
 *
 * @author Ma
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

	@Autowired
	private StringEncryptor stringEncryptor;

	/**
	 * 加密密码，用于配置文件
	 */
	@Test
	public void encrypt() {
		System.out.println(stringEncryptor.encrypt(""));
	}

}
