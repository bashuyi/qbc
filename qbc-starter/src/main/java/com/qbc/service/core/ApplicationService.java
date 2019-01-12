package com.qbc.service.core;

import org.springframework.beans.factory.annotation.Autowired;

import com.qbc.api.annotation.Api;
import com.qbc.api.annotation.ApiOperation;
import com.qbc.dto.core.ApplicationDTO;
import com.qbc.manager.core.ApiManageer;

/**
 * 应用服务
 *
 * @author Ma
 */
@Api(displayName = "应用服务")
public class ApplicationService {

	@Autowired
	private ApiManageer apiManageer;

	@ApiOperation(displayName = "获得应用信息")
	public ApplicationDTO info() {
		return apiManageer.getApplicationDTO();
	}

}
