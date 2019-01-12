package com.qbc.dto.core;

import java.util.List;

import lombok.Data;

/**
 * API操作的数据传输类
 *
 * @author Ma
 */
@Data
public class ApiOperationDTO {

	/** API操作名 */
	private String operationName;

	/** API操作描述 */
	private String operationDescription;

	/** API ID */
	private Long apiId;

	/** API参数列表 */
	private List<ApiParamDTO> apiParamList;

}
