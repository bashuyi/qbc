package com.qbc.dao;

import org.springframework.stereotype.Repository;

import com.slyak.spring.jpa.GenericJpaRepository;

/**
 * 提现表数据访问类
 *
 * @author Ma
 */
@Repository
public interface TxWithdrawalDAO extends GenericJpaRepository<TxWithdrawalDO, Long> {
	
}