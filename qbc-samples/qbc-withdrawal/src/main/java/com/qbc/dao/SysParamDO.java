package com.qbc.dao;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户表实体类
 *
 * @author Ma
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_param")
public class SysParamDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** 系统参数键 */
	private String paramKey;

	/** 系统参数名 */
	private String paramName;

	/** 系统参数值 */
	private String paramValue;

	/** 备注 */
	private String comment;

}
