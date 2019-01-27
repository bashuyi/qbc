package com.qbc.dao.system;

import com.slyak.spring.jpa.GenericJpaRepository;

/**
 * 消息表数据访问类
 *
 * @author Ma
 */
public interface SystemMessageDAO extends GenericJpaRepository<SystemMessageDO, Long> {

	/**
	 * 根据消息编码和地区查询
	 * 
	 * @param code   消息编码
	 * @param locale 地区
	 * @return 消息
	 */
	SystemMessageDO findByCodeAndLocaleAndDeletedFalse(String code, String locale);

}