package com.qbc.manager.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.qbc.manager.core.DatabaseInfoDTO;
import com.qbc.manager.core.DatabaseInfoManager;

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
