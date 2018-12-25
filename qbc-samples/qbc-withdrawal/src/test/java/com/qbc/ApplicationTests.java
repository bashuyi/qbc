package com.qbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.qbc.core.manager.CodeGeneratorManager;
import com.qbc.core.manager.DatabaseInfoDTO;
import com.qbc.core.manager.DatabaseInfoManager;

/**
 * 项目启动类的测试类
 *
 * @author Ma
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	private DatabaseInfoManager databaseInfoManager;

	@Autowired
	private CodeGeneratorManager codeGeneratorManager;

	/**
	 * 生成数据访问层
	 */
	@Test
	public void generateAll() {
		DatabaseInfoDTO databaseInfoDTO = databaseInfoManager.getDatabaseInfoBVO("tx");
		codeGeneratorManager.generateAll("DO", "com.qbc.dao", databaseInfoDTO);
		codeGeneratorManager.generateAll("DAO", "com.qbc.dao", databaseInfoDTO);
	}

}
