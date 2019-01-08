package com.qbc.dao;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户角色表实体类
 *
 * @author Ma
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_user_role")
public class SysUserRoleDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** 用户ID */
	private Long userId;

	/** 角色ID */
	private Long roleId;

}
