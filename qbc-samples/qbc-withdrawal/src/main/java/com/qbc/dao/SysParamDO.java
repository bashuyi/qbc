package com.qbc.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 系统用户表实体类
 *
 * @author Ma
 */
@Data
@Entity
@Table(name = "sys_param")
public class SysParamDO {

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "cn.izern.hibernate.id.IDSequenceGenerator")
	private Long id;

	private String createdBy;

	private java.time.LocalDateTime createdDateTime;

	private String lastModifiedBy;

	private java.time.LocalDateTime lastModifiedDateTime;

	private Boolean deleted;

	private String paramKey;

	private String paramName;

	private String paramValue;

	private String comment;

	@PrePersist
	public void prePersist() {
		createdBy = lastModifiedBy = "SYSTEM";
		createdDateTime = lastModifiedDateTime = java.time.LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		lastModifiedBy = "SYSTEM";
		lastModifiedDateTime = java.time.LocalDateTime.now();
	}

}
