package com.qbc.dao.common;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统变量数据访问实体类
 *
 * @author Ma
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "common_system_parameter")
public class CommonSystemParameterDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** 编码 */
	private String code;

	/** 表示名 */
	private String displayName;

	/** 内容 */
	private String content;

	/** 描述 */
	private String description;

}
