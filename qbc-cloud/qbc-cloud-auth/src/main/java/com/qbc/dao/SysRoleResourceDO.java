package com.qbc.dao;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统角色资源表实体类
 *
 * @author Ma
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_role_resource")
public class SysRoleResourceDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** 角色ID */
	private Long roleId;

	/** 资源ID */
	private Long resourceId;

}
