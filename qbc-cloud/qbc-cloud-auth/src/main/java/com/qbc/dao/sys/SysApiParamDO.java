package com.qbc.dao.sys;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统API参数表数据访问实体类
 *
 * @author Ma
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "sys_api_param")
public class SysApiParamDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** API参数名 */
	private String name;

	/** API参数表示名 */
	private String displayName;

	/** API参数描述 */
	private String description;

	/** API参数类型名 */
	private String typeName;

	/** 必须 */
	private Boolean required;

	/** API操作ID */
	private Long operationId;

}
