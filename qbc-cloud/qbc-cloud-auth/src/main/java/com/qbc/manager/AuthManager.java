package com.qbc.manager;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.qbc.dao.auth.AuthRoleDAO;
import com.qbc.dao.auth.AuthRoleDO;
import com.qbc.dao.auth.AuthRoleResourceDAO;
import com.qbc.dao.auth.AuthRoleResourceDO;

/**
 * 认证处理
 *
 * @author Ma
 */
@Component
public class AuthManager {

	@Autowired
	private AuthRoleResourceDAO authRoleResourceDAO;

	@Autowired
	private AuthRoleDAO authRoleDAO;

	@Autowired(required = false)
	private AuthManager authManager;

	@Cacheable(value = "ROLE_RESOURCE")
	public List<AuthRoleResourceDO> findByRoleName(String roleName) {
		return authRoleResourceDAO.findByRoleNameAndDeletedFalse(roleName);
	}

	@Cacheable(value = "USER_ROLE")
	public List<AuthRoleDO> searchByUsername(String username) {
		return authRoleDAO.searchByUsername(username);
	}

	/**
	 * 指定用戶是否有对应的资源的访问权限
	 * 
	 * @param username        用户名
	 * @param applicationName 应用名
	 * @param apiName         API名
	 * @param operationName   API操作名
	 * @return 是否有权限
	 */
	public boolean hasPermission(String username, String applicationName, String apiName, String operationName) {
		List<AuthRoleDO> authRoleDOs = authManager.searchByUsername(username);
		return authRoleDOs.stream().map(AuthRoleDO::getName).map(authManager::findByRoleName)
				.anyMatch(authRoleResourceDOs -> authRoleResourceDOs.stream().anyMatch(authRoleResourceDO -> {
					return StringUtils.equals(authRoleResourceDO.getApplicationName(), applicationName)
							&& StringUtils.equals(authRoleResourceDO.getApiName(), apiName)
							&& StringUtils.equals(authRoleResourceDO.getOperationName(), operationName);
				}));
	}

}
