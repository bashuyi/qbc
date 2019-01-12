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
	private String name;

	/** API操作表示名 */
	private String displayName;

	/** API操作描述 */
	private String description;

	/** API参数列表 */
	private List<ApiParamDTO> apiParamList;

}
