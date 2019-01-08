package com.qbc.dao;

import org.springframework.stereotype.Repository;

import com.slyak.spring.jpa.GenericJpaRepository;

/**
 * 系统用户角色表数据访问类
 *
 * @author Ma
 */
@Repository
public interface SysUserRoleDAO extends GenericJpaRepository<SysUserRoleDO, Long> {
	
}