package com.qbc.dao;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统用户表实体类
 *
 * @author Ma
 */
@Getter
@Setter
@Entity
@Table(name = "sys_user")
public class SysUserDO extends AbstractDO {

	/** 用户名 */
	private String username;

	/** 密码 */
	private String password;

}
