package com.qbc.dao.auth;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * API操作数据访问实体类
 *
 * @author Ma
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "auth_api_operation")
public class AuthApiOperationDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** 名称 */
	private String name;

	/** 表示名 */
	private String displayName;

	/** 描述 */
	private String description;

	/** API ID */
	private Long apiId;

}
