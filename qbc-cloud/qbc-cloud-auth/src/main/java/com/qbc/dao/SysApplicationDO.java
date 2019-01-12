package com.qbc.dao;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统应用表实体类
 *
 * @author Ma
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_application")
public class SysApplicationDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** 应用名 */
	private String applicationName;

	/** 应用描述 */
	private String applicationDescription;

}
