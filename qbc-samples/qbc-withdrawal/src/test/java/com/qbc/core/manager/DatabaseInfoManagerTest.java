package com.qbc.core.manager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 数据库信息处理类的测试类
 *
 * @author Ma
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseInfoManagerTest {

	@Autowired
	private DatabaseInfoManager databaseInfoManager;

	@Test
	public void testGetDatabaseInfoBVO() {
		DatabaseInfoDTO databaseInfoDTO = databaseInfoManager.getDatabaseInfoBVO();
		System.out.println(databaseInfoDTO);
	}

}
