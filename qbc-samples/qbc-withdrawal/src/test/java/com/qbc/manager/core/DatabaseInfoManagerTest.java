package com.qbc.manager.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.qbc.biz.core.DatabaseInfoBVO;
import com.qbc.biz.core.DatabaseInfoBIZ;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseInfoManagerTest {

	@Autowired
	private DatabaseInfoBIZ databaseInfoManager;

	@Test
	public void testGetDatabaseInfoBVO() {
		DatabaseInfoBVO databaseInfoDTO = databaseInfoManager.getDatabaseInfoBVO();
		System.out.println(databaseInfoDTO);
	}

}
