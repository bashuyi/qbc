package com.qbc.manager;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.qbc.constant.KeyGenerators;
import com.qbc.dao.SysRoleDAO;
import com.qbc.dao.SysRoleDO;
import com.qbc.dao.SysRoleResourceDAO;
import com.qbc.dao.SysRoleResourceDO;

/**
 * 认证处理
 *
 * @author Ma
 */
@Component
public class AuthManager {

	@Autowired
	private SysRoleResourceDAO sysRoleResourceDAO;

	@Autowired
	private SysRoleDAO sysRoleDAO;

	@Autowired(required = false)
	private AuthManager authManager;

	@Cacheable(value = "ROLE_RESOURCE", keyGenerator = KeyGenerators.REFLECT_KEY)
	public List<SysRoleResourceDO> findByRoleName(String roleName) {
		return sysRoleResourceDAO.findByRoleName(roleName);
	}

	@Cacheable(value = "USER_ROLE")
	public List<SysRoleDO> searchByUsername(String username) {
		return sysRoleDAO.searchByUsername(username);
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
		List<SysRoleDO> sysRoleDOs = authManager.searchByUsername(username);
		return sysRoleDOs.stream().map(SysRoleDO::getName).map(authManager::findByRoleName)
				.anyMatch(sysRoleResourceDOs -> sysRoleResourceDOs.stream().anyMatch(sysRoleResourceDO -> {
					return StringUtils.equals(sysRoleResourceDO.getApplicationName(), applicationName)
							&& StringUtils.equals(sysRoleResourceDO.getApiName(), apiName)
							&& StringUtils.equals(sysRoleResourceDO.getOperationName(), operationName);
				}));
	}

}
