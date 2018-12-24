package com.qbc.dao;

import org.springframework.stereotype.Repository;

import com.slyak.spring.jpa.GenericJpaRepository;

@Repository
public interface TxDepositDAO extends GenericJpaRepository<TxDepositDVO, Long> {
	
}