package com.qbc.dao.auth;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * API参数数据访问实体类
 *
 * @author Ma
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "auth_api_param")
public class AuthApiParamDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** 名称 */
	private String name;

	/** 表示名 */
	private String displayName;

	/** 描述 */
	private String description;

	/** 类型名 */
	private String typeName;

	/** 必须 */
	private Boolean required;

	/** API操作ID */
	private Long operationId;

}
