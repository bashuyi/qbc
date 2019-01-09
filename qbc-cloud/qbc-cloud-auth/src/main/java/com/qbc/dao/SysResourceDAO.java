package com.qbc.dao;

import org.springframework.stereotype.Repository;

import com.slyak.spring.jpa.GenericJpaRepository;

/**
 * 系统资源表数据访问类
 *
 * @author Ma
 */
@Repository
public interface SysResourceDAO extends GenericJpaRepository<SysResourceDO, Long> {
	
}