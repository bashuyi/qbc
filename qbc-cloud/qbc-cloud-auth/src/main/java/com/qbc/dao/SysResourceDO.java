package com.qbc.dao;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统资源表数据访问实体类
 *
 * @author Ma
 */
@Getter
@Setter
@ToString
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
