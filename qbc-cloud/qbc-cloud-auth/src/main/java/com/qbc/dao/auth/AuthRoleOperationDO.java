package com.qbc.dao.auth;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 角色操作表数据访问实体类
 *
 * @author Ma
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "auth_role_operation")
public class AuthRoleOperationDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** 角色ID */
	private Long roleId;

	/** API操作ID */
	private Long operationId;

}
