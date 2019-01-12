package com.qbc.dto.core;

import java.util.List;

import lombok.Data;

/**
 * 数据库信息的传输对象，用于封装数据库元信息
 *
 * @author Ma
 */
@Data
public class DatabaseInfoDTO {

	/**
	 * 数据库产品的名称
	 */
	private String databaseProductName;

	/**
	 * 数据库产品的版本号
	 */
	private String databaseProductVersion;

	/**
	 * 表信息实体的集合
	 */
	private List<TableInfoDTO> tableInfos;

}
