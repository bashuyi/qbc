package com.qbc.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "sys_param")
public class SysParamDVO {

	@Id
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
