package com.qbc.dao;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统服务表实体类
 *
 * @author Ma
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_service")
public class SysServiceDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** 服务名 */
	private String serviceName;

	/** Bean名 */
	private String beanName;

	/** 方法名 */
	private String methodName;

}
