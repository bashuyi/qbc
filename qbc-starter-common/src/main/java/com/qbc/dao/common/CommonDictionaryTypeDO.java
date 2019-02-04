package com.qbc.dao.common;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 数据字典类型数据访问实体类
 *
 * @author Ma
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "common_dictionary_type")
public class CommonDictionaryTypeDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** 表示名 */
	private String displayName;

	/** 描述 */
	private String description;

}
