package com.qbc.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.Data;

/**
 * 系统用户表实体类
 *
 * @author Ma
 */
@Data
@Entity
@Table(name = "sys_user")
public class SysUserDO {

	@Id
	private Long id;

	private String createdBy;

	private java.time.LocalDateTime createdDateTime;

	private String lastModifiedBy;

	private java.time.LocalDateTime lastModifiedDateTime;

	private Boolean deleted;

	private String username;

	private String password;

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
