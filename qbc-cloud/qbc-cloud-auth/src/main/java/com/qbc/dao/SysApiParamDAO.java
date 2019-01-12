package com.qbc.dao;

import org.springframework.stereotype.Repository;

import com.slyak.spring.jpa.GenericJpaRepository;

/**
 * 系统API参数表数据访问类
 *
 * @author Ma
 */
@Repository
public interface SysApiParamDAO extends GenericJpaRepository<SysApiParamDO, Long> {
	
}