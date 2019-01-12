package com.qbc.dto.core;

import java.util.List;

import lombok.Data;

/**
 * API的数据传输类
 *
 * @author Ma
 */
@Data
public class ApiDTO {

	/** API名 */
	private String apiName;

	/** API描述 */
	private String apiDescription;

	/** API操作列表 */
	private List<ApiOperationDTO> apiOperationList;

}
