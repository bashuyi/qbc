package com.qbc.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysParamDAOTest {

	@Autowired
	private SysParamDAO sysParamDAO;

	@Test
	public void testSave() {
		SysParamDO sysParamDO = new SysParamDO();
		sysParamDO.setParamKey("SYSTEM_NAME");
		sysParamDO.setParamName("系统名");
		sysParamDO.setParamValue("QBC");
		sysParamDAO.save(sysParamDO);
	}

}
