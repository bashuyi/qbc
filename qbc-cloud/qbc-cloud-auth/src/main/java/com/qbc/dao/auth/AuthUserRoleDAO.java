package com.qbc.dao.auth;

import com.slyak.spring.jpa.GenericJpaRepository;

/**
 * 用户角色数据访问类
 *
 * @author Ma
 */
public interface AuthUserRoleDAO extends GenericJpaRepository<AuthUserRoleDO, Long> {
	
}