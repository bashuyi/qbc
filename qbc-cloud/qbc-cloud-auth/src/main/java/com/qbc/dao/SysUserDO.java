package com.qbc.dao;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统用户表数据访问实体类
 *
 * @author Ma
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "sys_user")
public class SysUserDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** 用户名 */
	private String username;

	/** 密码 */
	private String password;

	/** 签名密钥 */
	private String secret;

	/** 用户表示名 */
	private String displayName;

}
