package com.qbc.dao;

import org.springframework.stereotype.Repository;

import com.slyak.spring.jpa.GenericJpaRepository;

/**
 * 系统用户表数据访问类
 *
 * @author Ma
 */
@Repository
public interface SysUserDAO extends GenericJpaRepository<SysUserDO, Long> {

	/**
	 * 根据用户名查询
	 * 
	 * @param username 用户名
	 * @return 用户实体
	 */
	SysUserDO findByUsername(String username);

}