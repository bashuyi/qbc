package com.qbc.dao.sys;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统应用API表数据访问实体类
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
	private String name;

	/** API表示名 */
	private String displayName;

	/** API描述 */
	private String description;

	/** 应用ID */
	private Long applicationId;

}
