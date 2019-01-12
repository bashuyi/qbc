package com.qbc.dao;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统应用表实体类
 *
 * @author Ma
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "sys_application")
public class SysApplicationDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** 应用名 */
	private String applicationName;

	/** 应用描述 */
	private String applicationDescription;

}
