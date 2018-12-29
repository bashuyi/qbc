package com.qbc.dao;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.core.dao.AbstractDO;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统用户表实体类
 *
 * @author Ma
 */
@Getter
@Setter
@Entity
@Table(name = "sys_param")
public class SysParamDO extends AbstractDO {

	/** 系统参数键 */
	private String paramKey;

	/** 系统参数名 */
	private String paramName;

	/** 系统参数值 */
	private String paramValue;

	/** 备注 */
	private String comment;

}
