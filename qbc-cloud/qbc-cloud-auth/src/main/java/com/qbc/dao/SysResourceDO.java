package com.qbc.dao;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统资源表实体类
 *
 * @author Ma
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_resource")
public class SysResourceDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** 应用ID */
	private String applicationId;

	/** API ID */
	private String apiId;

	/** API操作ID */
	private String operationId;

}
