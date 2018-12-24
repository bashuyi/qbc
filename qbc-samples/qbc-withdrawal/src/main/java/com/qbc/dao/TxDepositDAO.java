package com.qbc.dao;

import org.springframework.stereotype.Repository;

import com.slyak.spring.jpa.GenericJpaRepository;

/**
 * 充值表数据访问类
 *
 * @author Ma
 */
@Repository
public interface TxDepositDAO extends GenericJpaRepository<TxDepositDO, Long> {
	
}