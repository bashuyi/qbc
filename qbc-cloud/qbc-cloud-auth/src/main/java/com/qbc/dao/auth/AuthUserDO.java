package com.qbc.dao.auth;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户数据访问实体类
 *
 * @author Ma
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "auth_user")
public class AuthUserDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** 用户名 */
	private String username;

	/** 密码 */
	private String password;

	/** 签名密钥 */
	private String secret;

	/** 表示名 */
	private String displayName;

}
