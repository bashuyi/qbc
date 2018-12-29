package com.qbc.dao;

import org.springframework.stereotype.Repository;

import com.slyak.spring.jpa.GenericJpaRepository;

/**
 * 系统用户表数据访问类
 *
 * @author Ma
 */
@Repository
public interface SysParamDAO extends GenericJpaRepository<SysParamDO, Long> {
	
}