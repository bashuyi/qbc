package com.qbc.manager.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeGeneratorManagerTest {

	@Autowired
	private DatabaseInfoManager databaseInfoManager;

	@Autowired
	private CodeGeneratorManager codeGeneratorManager;

	/**
	 * 生成数据访问层
	 */
	@Test
	public void testGenerateAll() {
		DatabaseInfoDTO databaseInfoDTO = databaseInfoManager.getDatabaseInfoDTO();
		codeGeneratorManager.generateAll("DO", "com.qbc.dao", databaseInfoDTO);
		codeGeneratorManager.generateAll("DAO", "com.qbc.dao", databaseInfoDTO);
	}

}
