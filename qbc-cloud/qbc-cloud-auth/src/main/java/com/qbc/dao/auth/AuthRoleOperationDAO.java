package com.qbc.dao.auth;

import java.util.List;

import com.slyak.spring.jpa.GenericJpaRepository;

/**
 * 角色操作表数据访问类
 *
 * @author Ma
 */
public interface AuthRoleOperationDAO extends GenericJpaRepository<AuthRoleOperationDO, Long> {

	/**
	 * 根据角色ID查询
	 * 
	 * @param roleId 角色ID
	 * @return 角色操作
	 */
	List<AuthRoleOperationDO> findByRoleIdAndDeletedFalse(Long roleId);
	
}