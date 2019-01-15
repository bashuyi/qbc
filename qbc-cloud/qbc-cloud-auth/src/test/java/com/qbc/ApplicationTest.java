package com.qbc;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.qbc.dao.sys.SysApiOperationDAO;
import com.qbc.dao.sys.SysApiOperationDO;
import com.qbc.dao.sys.SysRoleDAO;
import com.qbc.dao.sys.SysRoleDO;
import com.qbc.dao.sys.SysRoleOperationDAO;
import com.qbc.dao.sys.SysRoleOperationDO;
import com.qbc.dao.sys.SysUserDAO;
import com.qbc.dao.sys.SysUserDO;
import com.qbc.dao.sys.SysUserRoleDAO;
import com.qbc.dao.sys.SysUserRoleDO;
import com.qbc.dto.core.ApplicationDTO;
import com.qbc.dto.core.DatabaseInfoDTO;
import com.qbc.manager.ApiDataManager;
import com.qbc.manager.core.ApiManageer;
import com.qbc.manager.core.CodeGeneratorManager;
import com.qbc.manager.core.DatabaseInfoManager;
import com.qbc.utils.core.SnowflakeUtils;

/**
 * 应用测试类，用于代码和数据生成
 *
 * @author Ma
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

	@Autowired
	private DatabaseInfoManager databaseInfoManager;

	@Autowired
	private CodeGeneratorManager codeGeneratorManager;

	@Autowired
	private ApiManageer apiManageer;

	@Autowired
	private ApiDataManager apiDataManager;

	@Autowired
	private SysApiOperationDAO sysApiOperationDAO;

	@Autowired
	private SysRoleDAO sysRoleDAO;

	@Autowired
	private SysRoleOperationDAO sysRoleOperationDAO;

	@Autowired
	private SysUserDAO sysUserDAO;

	@Autowired
	private SysUserRoleDAO sysUserRoleDAO;

	@Test
	public void generateCode() {
		DatabaseInfoDTO databaseInfoDTO = databaseInfoManager.getDatabaseInfoDTO();
		codeGeneratorManager.generateAll("DAO", "com.qbc.dao", databaseInfoDTO);
//		codeGeneratorManager.generateAll("DO", "com.qbc.dao", databaseInfoDTO);
	}

	@Test
	public void generateOneCode() {
		DatabaseInfoDTO databaseInfoDTO = databaseInfoManager.getDatabaseInfoDTO(null, "sys_role_resource");
		codeGeneratorManager.generateAll("DAO", "com.qbc.dao", databaseInfoDTO);
		codeGeneratorManager.generateAll("DO_VIEW", "com.qbc.dao", databaseInfoDTO);
	}

	@Test
	public void generateApplicationData() {
		ApplicationDTO applicationDTO = apiManageer.getApplicationDTO();
		apiDataManager.save(applicationDTO);
	}

	@Test
	public void generateRoleData() {
		SysRoleDO sysRoleDO = new SysRoleDO();
		sysRoleDO.setName("admin");
		sysRoleDO.setDisplayName("超级管理员");
		sysRoleDO = sysRoleDAO.save(sysRoleDO);

		List<SysApiOperationDO> sysApiOperationDOs = sysApiOperationDAO.findAll();
		for (SysApiOperationDO sysApiOperationDO : sysApiOperationDOs) {
			SysRoleOperationDO sysRoleOperationDO = new SysRoleOperationDO();
			sysRoleOperationDO.setRoleId(sysRoleDO.getId());
			sysRoleOperationDO.setOperationId(sysApiOperationDO.getId());
			sysRoleOperationDAO.save(sysRoleOperationDO);
		}
	}

	@Test
	public void generateUserData() {
		SysUserDO sysUserDO = new SysUserDO();
		sysUserDO.setUsername("qbc");
		sysUserDO.setPassword("123456");
		sysUserDO.setSecret(SnowflakeUtils.nextString());
		sysUserDO = sysUserDAO.save(sysUserDO);

		List<SysRoleDO> sysRoleDOs = sysRoleDAO.findAll();
		for (SysRoleDO sysRoleDO : sysRoleDOs) {
			SysUserRoleDO sysUserRoleDO = new SysUserRoleDO();
			sysUserRoleDO.setUserId(sysUserDO.getId());
			sysUserRoleDO.setRoleId(sysRoleDO.getId());
			sysUserRoleDAO.save(sysUserRoleDO);
		}
	}

}
