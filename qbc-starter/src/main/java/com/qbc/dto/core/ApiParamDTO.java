package com.qbc.dto.core;

import lombok.Data;

/**
 * API参数的数据传输类
 *
 * @author Ma
 */
@Data
public class ApiParamDTO {

	/** API参数名 */
	private String name;

	/** API参数表示名 */
	private String displayName;

	/** API参数描述 */
	private String description;

	/** API参数类型名 */
	private String typeName;

	/** 必须 */
	private Boolean required;

}
