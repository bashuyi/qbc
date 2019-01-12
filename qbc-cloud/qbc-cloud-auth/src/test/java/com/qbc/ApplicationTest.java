package com.qbc;

import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.qbc.dao.SysApiDAO;
import com.qbc.dao.SysApiDO;
import com.qbc.dao.SysApiOperationDAO;
import com.qbc.dao.SysApiOperationDO;
import com.qbc.dao.SysApiParamDAO;
import com.qbc.dao.SysApiParamDO;
import com.qbc.dao.SysApplicationDAO;
import com.qbc.dao.SysApplicationDO;
import com.qbc.dto.core.ApiDTO;
import com.qbc.dto.core.ApiOperationDTO;
import com.qbc.dto.core.ApiParamDTO;
import com.qbc.dto.core.ApplicationDTO;
import com.qbc.dto.core.DatabaseInfoDTO;
import com.qbc.manager.core.ApiManageer;
import com.qbc.manager.core.CodeGeneratorManager;
import com.qbc.manager.core.DatabaseInfoManager;

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
	private DozerBeanMapper dozerBeanMapper;

	@Autowired
	private SysApplicationDAO sysApplicationDAO;

	@Autowired
	private SysApiDAO sysApiDAO;

	@Autowired
	private SysApiOperationDAO sysApiOperationDAO;

	@Autowired
	private SysApiParamDAO sysApiParamDAO;

	@Test
	public void generateCode() {
		DatabaseInfoDTO databaseInfoDTO = databaseInfoManager.getDatabaseInfoDTO();
		codeGeneratorManager.generateAll("DAO", "com.qbc.dao", databaseInfoDTO);
		codeGeneratorManager.generateAll("DO", "com.qbc.dao", databaseInfoDTO);
	}

	@Test
	public void generateApplicationData() {
		ApplicationDTO applicationDTO = apiManageer.getApplicationDTO();

		SysApplicationDO SysApplicationDO = dozerBeanMapper.map(applicationDTO, SysApplicationDO.class);
		SysApplicationDO = sysApplicationDAO.save(SysApplicationDO);

		for (ApiDTO apiDTO : applicationDTO.getApiList()) {
			SysApiDO sysApiDO = dozerBeanMapper.map(apiDTO, SysApiDO.class);
			sysApiDO.setApplicationId(SysApplicationDO.getId());
			sysApiDO = sysApiDAO.save(sysApiDO);

			for (ApiOperationDTO apiOperationDTO : apiDTO.getApiOperationList()) {
				SysApiOperationDO sysApiOperationDO = dozerBeanMapper.map(apiOperationDTO, SysApiOperationDO.class);
				sysApiOperationDO.setApiId(sysApiDO.getId());
				sysApiOperationDO = sysApiOperationDAO.save(sysApiOperationDO);

				for (ApiParamDTO apiParamDTO : apiOperationDTO.getApiParamList()) {
					SysApiParamDO sysApiParamDO = dozerBeanMapper.map(apiParamDTO, SysApiParamDO.class);
					sysApiParamDO.setOperationId(sysApiOperationDO.getId());
					sysApiParamDAO.save(sysApiParamDO);
				}
			}
		}

	}

}
