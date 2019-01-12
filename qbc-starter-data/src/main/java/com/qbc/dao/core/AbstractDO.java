package com.qbc.dao.core;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.qbc.utils.core.SnowflakeUtils;
import com.qbc.utils.core.UserUtils;

import lombok.Data;

/**
 * 通用数据访问实体，定义共通字段并在持久化前自动设置
 *
 * @author Ma
 */
@Data
@MappedSuperclass
public abstract class AbstractDO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** ID */
	@Id
	protected Long id;

	/** 创建者 */
	protected Long createdBy;

	/** 创建时间 */
	protected LocalDateTime createdDateTime;

	/** 最后更新者 */
	protected Long lastModifiedBy;

	/** 最后更新时间 */
	protected LocalDateTime lastModifiedDateTime;

	/** 已删除 */
	protected Boolean deleted;

	@PrePersist
	public void prePersist() {
		id = SnowflakeUtils.nextId();
		createdBy = lastModifiedBy = UserUtils.getUserId();
		createdDateTime = lastModifiedDateTime = LocalDateTime.now();
		deleted = false;
	}

	@PreUpdate
	public void preUpdate() {
		lastModifiedBy = UserUtils.getUserId();
		lastModifiedDateTime = LocalDateTime.now();
	}

}
