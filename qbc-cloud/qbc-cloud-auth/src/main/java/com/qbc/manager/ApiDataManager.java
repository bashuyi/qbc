package com.qbc.manager;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qbc.dao.auth.AuthApiDAO;
import com.qbc.dao.auth.AuthApiDO;
import com.qbc.dao.auth.AuthApiOperationDAO;
import com.qbc.dao.auth.AuthApiOperationDO;
import com.qbc.dao.auth.AuthApiParamDAO;
import com.qbc.dao.auth.AuthApiParamDO;
import com.qbc.dao.auth.AuthApplicationDAO;
import com.qbc.dao.auth.AuthApplicationDO;
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
	private AuthApplicationDAO authApplicationDAO;

	@Autowired
	private AuthApiDAO authApiDAO;

	@Autowired
	private AuthApiOperationDAO authApiOperationDAO;

	@Autowired
	private AuthApiParamDAO authApiParamDAO;

	public void save(ApplicationDTO applicationDTO) {
		AuthApplicationDO authApplicationDO = dozerBeanMapper.map(applicationDTO, AuthApplicationDO.class);
		authApplicationDO = authApplicationDAO.save(authApplicationDO);

		for (ApiDTO apiDTO : applicationDTO.getApiList()) {
			AuthApiDO authApiDO = dozerBeanMapper.map(apiDTO, AuthApiDO.class);
			authApiDO.setApplicationId(authApplicationDO.getId());
			authApiDO = authApiDAO.save(authApiDO);

			for (ApiOperationDTO apiOperationDTO : apiDTO.getApiOperationList()) {
				AuthApiOperationDO authApiOperationDO = dozerBeanMapper.map(apiOperationDTO, AuthApiOperationDO.class);
				authApiOperationDO.setApiId(authApiDO.getId());
				authApiOperationDO = authApiOperationDAO.save(authApiOperationDO);

				for (ApiParamDTO apiParamDTO : apiOperationDTO.getApiParamList()) {
					AuthApiParamDO authApiParamDO = dozerBeanMapper.map(apiParamDTO, AuthApiParamDO.class);
					authApiParamDO.setOperationId(authApiOperationDO.getId());
					authApiParamDAO.save(authApiParamDO);
				}
			}
		}
	}

}
