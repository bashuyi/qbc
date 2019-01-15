package com.qbc.dao.sys;

import java.util.List;

import com.slyak.spring.jpa.GenericJpaRepository;

/**
 * 系统角色资源视图数据访问类
 *
 * @author Ma
 */
public interface SysRoleResourceDAO extends GenericJpaRepository<SysRoleResourceDO, Long> {

	/**
	 * 根据角色名查询
	 * 
	 * @param roleName 角色名
	 * @return 角色资源列表
	 */
	List<SysRoleResourceDO> findByRoleName(String roleName);

}