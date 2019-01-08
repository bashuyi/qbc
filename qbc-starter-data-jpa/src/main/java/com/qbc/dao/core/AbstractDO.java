package com.qbc.dao.core;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.qbc.utils.core.SnowflakeUtils;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class AbstractDO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** ID */
	@Id
	protected Long id;

	/** 创建者 */
	protected String createdBy;

	/** 创建时间 */
	protected LocalDateTime createdDateTime;

	/** 最后更新者 */
	protected String lastModifiedBy;

	/** 最后更新时间 */
	protected LocalDateTime lastModifiedDateTime;

	/** 已删除 */
	protected Boolean deleted;

	@PrePersist
	public void prePersist() {
		id = SnowflakeUtils.nextId();
		createdBy = lastModifiedBy = "SYSTEM";
		createdDateTime = lastModifiedDateTime = LocalDateTime.now();
		deleted = false;
	}

	@PreUpdate
	public void preUpdate() {
		lastModifiedBy = "SYSTEM";
		lastModifiedDateTime = LocalDateTime.now();
	}

}
