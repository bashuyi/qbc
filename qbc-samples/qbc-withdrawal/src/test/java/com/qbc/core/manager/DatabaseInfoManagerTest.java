package com.qbc.core.manager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseInfoManagerTest {

	@Autowired
	private DatabaseInfoManager databaseInfoManager;

	@Test
	public void testGetDatabaseInfoBVO() {
		DatabaseInfoDTO databaseInfoDTO = databaseInfoManager.getDatabaseInfoBVO("tx");
		System.out.println(databaseInfoDTO);
	}

}
