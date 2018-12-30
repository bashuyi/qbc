package com.qbc.dao;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户表实体类
 *
 * @author Ma
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_user")
public class SysUserDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** 用户名 */
	private String username;

	/** 密码 */
	private String password;

}
