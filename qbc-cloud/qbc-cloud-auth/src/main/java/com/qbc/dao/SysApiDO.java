package com.qbc.dao;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统应用API表实体类
 *
 * @author Ma
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "sys_api")
public class SysApiDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** API名 */
	private String apiName;

	/** API描述 */
	private String apiDescription;

	/** 应用ID */
	private Long applicationId;

}
