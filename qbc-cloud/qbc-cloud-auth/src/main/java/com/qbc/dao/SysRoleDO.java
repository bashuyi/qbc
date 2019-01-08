package com.qbc.dao;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统角色表实体类
 *
 * @author Ma
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_role")
public class SysRoleDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** 角色名 */
	private String roleName;

	/** 角色描述 */
	private String roleDescription;

}
