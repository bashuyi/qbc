package com.qbc.dto.core;

import java.util.List;

import lombok.Data;

/**
 * 应用的数据传输类，用于封装整个应用的API信息
 *
 * @author Ma
 */
@Data
public class ApplicationDTO {

	/** 应用名 */
	private String name;

	/** 应用表示名 */
	private String displayName;

	/** 应用描述 */
	private String description;

	/** 应用的API列表 */
	private List<ApiDTO> apiList;

}
