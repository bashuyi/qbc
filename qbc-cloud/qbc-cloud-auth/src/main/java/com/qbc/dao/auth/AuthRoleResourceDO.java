package com.qbc.dao.auth;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 角色资源视图数据访问实体类
 *
 * @author Ma
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "auth_role_resource")
public class AuthRoleResourceDO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** API操作名 */
	@Id
	private String operationName;

	/** API操作表示名 */
	private String operationDisplayName;

	/** API操作描述 */
	private String operationDescription;

	/** API名 */
	private String apiName;

	/** API表示名 */
	private String apiDisplayName;

	/** API描述 */
	private String apiDescription;

	/** 应用名 */
	private String applicationName;

	/** 应用表示名 */
	private String applicationDisplayName;

	/** 应用描述 */
	private String applicationDescription;

	/** 角色名 */
	private String roleName;

	/** 角色表示名 */
	private String roleDisplayName;

	/** 角色描述 */
	private String roleDescription;

}
