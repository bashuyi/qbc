package com.qbc.dao;

import org.springframework.stereotype.Repository;

import com.slyak.spring.jpa.GenericJpaRepository;

/**
 * 系统服务表数据访问类
 *
 * @author Ma
 */
@Repository
public interface SysServiceDAO extends GenericJpaRepository<SysServiceDO, Long> {
	
}