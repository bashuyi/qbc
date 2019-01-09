package com.qbc.dao;

import org.springframework.stereotype.Repository;

import com.slyak.spring.jpa.GenericJpaRepository;

/**
 * 系统角色资源表数据访问类
 *
 * @author Ma
 */
@Repository
public interface SysRoleResourceDAO extends GenericJpaRepository<SysRoleResourceDO, Long> {
	
}