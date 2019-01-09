package com.qbc.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.qbc.manager.core.ResourceDTO;
import com.qbc.manager.core.ResourceManager;
import com.qbc.openinterface.OpenInterface;
import com.qbc.openinterface.OpenInterfaceMapResponse;
import com.qbc.openinterface.OpenInterfaceMethod;

@Validated
@OpenInterface(description = "资源服务")
public class ResourceService {

	@Autowired
	private ResourceManager resourceManager;

	@OpenInterfaceMethod(description = "获得服务列表")
	public ResourceDTO listResources(OpenInterfaceMapResponse openInterfaceMapResponse) {
		return resourceManager.getResourceDTO();
	}

}
