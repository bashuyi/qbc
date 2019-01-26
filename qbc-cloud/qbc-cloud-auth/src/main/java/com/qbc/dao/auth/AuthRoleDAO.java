package com.qbc.dao.auth;

import java.util.List;

import com.slyak.spring.jpa.GenericJpaRepository;
import com.slyak.spring.jpa.TemplateQuery;

/**
 * 角色表数据访问类
 *
 * @author Ma
 */
public interface AuthRoleDAO extends GenericJpaRepository<AuthRoleDO, Long> {

	/**
	 * 根据用户名查询角色列表
	 * 
	 * @param username 用户名
	 * @return 角色列表
	 */
	@TemplateQuery
	List<AuthRoleDO> searchByUsername(String username);
	
}