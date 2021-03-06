package com.qbc.dao.auth;

import com.slyak.spring.jpa.GenericJpaRepository;

/**
 * 用户数据访问类
 *
 * @author Ma
 */
public interface AuthUserDAO extends GenericJpaRepository<AuthUserDO, Long> {

	/**
	 * 根据用户名查询
	 * 
	 * @param username 用户名
	 * @return 用户实体
	 */
	AuthUserDO findByUsernameAndDeletedFalse(String username);
	
}