package com.qbc.manager;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qbc.dao.sys.SysApiDAO;
import com.qbc.dao.sys.SysApiDO;
import com.qbc.dao.sys.SysApiOperationDAO;
import com.qbc.dao.sys.SysApiOperationDO;
import com.qbc.dao.sys.SysApiParamDAO;
import com.qbc.dao.sys.SysApiParamDO;
import com.qbc.dao.sys.SysApplicationDAO;
import com.qbc.dao.sys.SysApplicationDO;
import com.qbc.dto.core.ApiDTO;
import com.qbc.dto.core.ApiOperationDTO;
import com.qbc.dto.core.ApiParamDTO;
import com.qbc.dto.core.ApplicationDTO;

/**
 * API数据处理
 *
 * @author Ma
 */
@Component
public class ApiDataManager {

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

	public void save(ApplicationDTO applicationDTO) {
		SysApplicationDO sysApplicationDO = dozerBeanMapper.map(applicationDTO, SysApplicationDO.class);
		sysApplicationDO = sysApplicationDAO.save(sysApplicationDO);

		for (ApiDTO apiDTO : applicationDTO.getApiList()) {
			SysApiDO sysApiDO = dozerBeanMapper.map(apiDTO, SysApiDO.class);
			sysApiDO.setApplicationId(sysApplicationDO.getId());
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
