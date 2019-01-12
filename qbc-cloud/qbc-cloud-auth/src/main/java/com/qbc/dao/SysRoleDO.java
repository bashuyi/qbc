package com.qbc.dao;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统角色表数据访问实体类
 *
 * @author Ma
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "sys_role")
public class SysRoleDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** 角色名 */
	private String name;

	/** 角色表示名 */
	private String displayName;

	/** 角色描述 */
	private String description;

}
