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
	private String paramName;

	/** API参数描述 */
	private String paramDescription;

	/** API参数类型名 */
	private String paramTypeName;

	/** 必须 */
	private Boolean required;

	/** API操作ID */
	private Long operationId;

}
