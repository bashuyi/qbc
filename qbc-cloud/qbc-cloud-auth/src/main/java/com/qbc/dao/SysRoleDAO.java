package com.qbc.dao;

import java.util.List;

import com.slyak.spring.jpa.GenericJpaRepository;
import com.slyak.spring.jpa.TemplateQuery;

/**
 * 系统角色表数据访问类
 *
 * @author Ma
 */
public interface SysRoleDAO extends GenericJpaRepository<SysRoleDO, Long> {

	@TemplateQuery
	List<SysRoleDO> searchByUsername(String username);

}