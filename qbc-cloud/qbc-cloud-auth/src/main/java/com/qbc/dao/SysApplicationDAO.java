package com.qbc.dao;

import org.springframework.stereotype.Repository;

import com.slyak.spring.jpa.GenericJpaRepository;

/**
 * 系统应用表数据访问类
 *
 * @author Ma
 */
@Repository
public interface SysApplicationDAO extends GenericJpaRepository<SysApplicationDO, Long> {
	
}