package com.qbc.dao.common;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 消息表数据访问实体类
 *
 * @author Ma
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "common_message")
public class CommonMessageDO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** ID */
	@Id
	private Long id;

	/** 创建者 */
	private Long createdBy;

	/** 创建时间 */
	private java.time.LocalDateTime createdDateTime;

	/** 修改者 */
	private Long lastModifiedBy;

	/** 修改时间 */
	private java.time.LocalDateTime lastModifiedDateTime;

	/** 已删除 */
	private Boolean deleted;

	/** 消息编码 */
	private String code;

	/** 地区 */
	private String locale;

	/** 消息文本 */
	private String text;

}
