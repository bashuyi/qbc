package com.qbc.dao;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统API参数表实体类
 *
 * @author Ma
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_api_param")
public class SysApiParamDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** API参数名 */
	private String paramName;

	/** API参数描述 */
	private String paramDescription;

	/** API参数类型名 */
	private String paramTypeName;

	/** 必须 */
	private Boolean required;

	/** API操作ID */
	private Long operationId;

}
