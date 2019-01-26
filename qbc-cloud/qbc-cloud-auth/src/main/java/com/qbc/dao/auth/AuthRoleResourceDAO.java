package com.qbc.dao.auth;

import java.util.List;

import com.slyak.spring.jpa.GenericJpaRepository;

/**
 * 角色资源视图数据访问类
 *
 * @author Ma
 */
public interface AuthRoleResourceDAO extends GenericJpaRepository<AuthRoleResourceDO, Long> {

	/**
	 * 根据角色名查询
	 * 
	 * @param roleName 角色名
	 * @return 角色资源列表
	 */
	List<AuthRoleResourceDO> findByRoleName(String roleName);
	
}