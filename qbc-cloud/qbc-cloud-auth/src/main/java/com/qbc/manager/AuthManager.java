package com.qbc.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qbc.dao.SysRoleResourceDAO;
import com.qbc.dao.SysRoleResourceDO;

@Component
public class AuthManager {

	@Autowired
	private SysRoleResourceDAO sysRoleResourceDAO;

	public List<SysRoleResourceDO> findByRoleName(String roleName) {
		return sysRoleResourceDAO.findByRoleName(roleName);
	}

}
