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
	private String name;

	/** API表示名 */
	private String displayName;

	/** API描述 */
	private String description;

	/** API操作列表 */
	private List<ApiOperationDTO> apiOperationList;

}
