package com.qbc.dao.auth;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 应用API表数据访问实体类
 *
 * @author Ma
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "auth_api")
public class AuthApiDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** API名 */
	private String name;

	/** API表示名 */
	private String displayName;

	/** API描述 */
	private String description;

	/** 应用ID */
	private Long applicationId;

}
