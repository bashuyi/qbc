package com.qbc.dao;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统API操作表数据访问实体类
 *
 * @author Ma
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "sys_api_operation")
public class SysApiOperationDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** API操作名 */
	private String operationName;

	/** API操作描述 */
	private String operationDescription;

	/** API ID */
	private Long apiId;

}
