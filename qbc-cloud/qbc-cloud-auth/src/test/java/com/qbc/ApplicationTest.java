package com.qbc;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.qbc.dao.auth.AuthApiOperationDAO;
import com.qbc.dao.auth.AuthApiOperationDO;
import com.qbc.dao.auth.AuthRoleDAO;
import com.qbc.dao.auth.AuthRoleDO;
import com.qbc.dao.auth.AuthRoleOperationDAO;
import com.qbc.dao.auth.AuthRoleOperationDO;
import com.qbc.dao.auth.AuthUserDAO;
import com.qbc.dao.auth.AuthUserDO;
import com.qbc.dao.auth.AuthUserRoleDAO;
import com.qbc.dao.auth.AuthUserRoleDO;
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
	private AuthApiOperationDAO authApiOperationDAO;

	@Autowired
	private AuthRoleDAO authRoleDAO;

	@Autowired
	private AuthRoleOperationDAO authRoleOperationDAO;

	@Autowired
	private AuthUserDAO authUserDAO;

	@Autowired
	private AuthUserRoleDAO authUserRoleDAO;

	@Test
	public void generateCode() {
		DatabaseInfoDTO databaseInfoDTO = databaseInfoManager.getDatabaseInfoDTO();
		codeGeneratorManager.generateAll("DAO", "com.qbc.dao.auth", databaseInfoDTO);
		codeGeneratorManager.generateAll("DO", "com.qbc.dao.auth", databaseInfoDTO);
	}

	@Test
	public void generateOneCode() {
		DatabaseInfoDTO databaseInfoDTO = databaseInfoManager.getDatabaseInfoDTO(null, "system_message");
		codeGeneratorManager.generateAll("DAO", "com.qbc.dao.system", databaseInfoDTO);
		codeGeneratorManager.generateAll("DO_VIEW", "com.qbc.dao.system", databaseInfoDTO);
	}

	@Test
	public void generateApplicationData() {
		ApplicationDTO applicationDTO = apiManageer.getApplicationDTO();
		apiDataManager.save(applicationDTO);
	}

	@Test
	public void generateRoleData() {
		AuthRoleDO authRoleDO = new AuthRoleDO();
		authRoleDO.setName("admin");
		authRoleDO.setDisplayName("超级管理员");
		authRoleDO = authRoleDAO.save(authRoleDO);

		List<AuthApiOperationDO> authApiOperationDOs = authApiOperationDAO.findAll();
		for (AuthApiOperationDO authApiOperationDO : authApiOperationDOs) {
			AuthRoleOperationDO authRoleOperationDO = new AuthRoleOperationDO();
			authRoleOperationDO.setRoleId(authRoleDO.getId());
			authRoleOperationDO.setOperationId(authApiOperationDO.getId());
			authRoleOperationDAO.save(authRoleOperationDO);
		}
	}

	@Test
	public void generateUserData() {
		AuthUserDO authUserDO = new AuthUserDO();
		authUserDO.setUsername("qbc");
		authUserDO.setPassword("123456");
		authUserDO.setSecret(SnowflakeUtils.nextString());
		authUserDO = authUserDAO.save(authUserDO);

		List<AuthRoleDO> authRoleDOs = authRoleDAO.findAll();
		for (AuthRoleDO authRoleDO : authRoleDOs) {
			AuthUserRoleDO authUserRoleDO = new AuthUserRoleDO();
			authUserRoleDO.setUserId(authUserDO.getId());
			authUserRoleDO.setRoleId(authRoleDO.getId());
			authUserRoleDAO.save(authUserRoleDO);
		}
	}

}
