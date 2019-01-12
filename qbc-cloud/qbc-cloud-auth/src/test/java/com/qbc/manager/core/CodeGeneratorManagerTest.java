package com.qbc.manager.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.qbc.dto.core.DatabaseInfoDTO;

/**
 * 代码生成执行类
 *
 * @author Ma
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeGeneratorManagerTest {

	@Autowired
	private DatabaseInfoManager databaseInfoManager;

	@Autowired
	private CodeGeneratorManager codeGeneratorManager;

	@Test
	public void testGenerateAll() {
		DatabaseInfoDTO databaseInfoDTO = databaseInfoManager.getDatabaseInfoDTO();
//		codeGeneratorManager.generateAll("DAO", "com.qbc.dao", databaseInfoDTO);
		codeGeneratorManager.generateAll("DO", "com.qbc.dao", databaseInfoDTO);
	}

}
