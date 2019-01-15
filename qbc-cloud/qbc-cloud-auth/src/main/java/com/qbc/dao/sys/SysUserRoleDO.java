package com.qbc.dao.sys;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统用户角色表数据访问实体类
 *
 * @author Ma
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "sys_user_role")
public class SysUserRoleDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** 用户ID */
	private Long userId;

	/** 角色ID */
	private Long roleId;

}
