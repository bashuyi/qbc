package com.qbc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.slyak.spring.jpa.GenericJpaRepository;

/**
 * 系统角色操作表数据访问类
 *
 * @author Ma
 */
@Repository
public interface SysRoleOperationDAO extends GenericJpaRepository<SysRoleOperationDO, Long> {

	/**
	 * 根据角色ID查询
	 * 
	 * @param roleId 角色ID
	 * @return 角色操作
	 */
	List<SysRoleOperationDO> findByRoleIdAndDeletedFalse(Long roleId);

}