package com.qbc.dao;

import java.util.List;

import com.slyak.spring.jpa.GenericJpaRepository;
import com.slyak.spring.jpa.TemplateQuery;

/**
 * 系统角色表数据访问类
 *
 * @author Ma
 */
public interface SysRoleDAO extends GenericJpaRepository<SysRoleDO, Long> {

	/**
	 * 根据用户名查询角色列表
	 * 
	 * @param username 用户名
	 * @return 角色列表
	 */
	@TemplateQuery
	List<SysRoleDO> searchByUsername(String username);

}