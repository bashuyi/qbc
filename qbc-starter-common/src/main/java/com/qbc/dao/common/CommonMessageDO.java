package com.qbc.dao.common;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qbc.dao.core.AbstractDO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 消息数据访问实体类
 *
 * @author Ma
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "common_message")
public class CommonMessageDO extends AbstractDO {

	private static final long serialVersionUID = 1L;

	/** 编码 */
	private String code;

	/** 语言 */
	private String lang;

	/** 内容 */
	private String content;

}
