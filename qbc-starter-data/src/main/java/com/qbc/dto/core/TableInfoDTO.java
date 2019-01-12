package com.qbc.dto.core;

import java.util.List;

import lombok.Data;

/**
 * 表信息的传输对象
 *
 * @author Ma
 */
@Data
public class TableInfoDTO {

	/**
	 * 表类别（可为 null）
	 */
	private String tableCat;

	/**
	 * 表模式（可为 null）
	 */
	private String tableSchem;

	/**
	 * 表名称
	 */
	private String tableName;

	/**
	 * 表类型。<br/>
	 * 典型的类型是 "TABLE"、"VIEW"、"SYSTEM TABLE"、"GLOBAL TEMPORARY"、<br/>
	 * "LOCAL TEMPORARY"、"ALIAS" 和 "SYNONYM"。
	 */
	private String tableType;

	/**
	 * 表的解释性注释
	 */
	private String remarks;

	/**
	 * 列信息实体的集合
	 */
	private List<ColumnInfoDTO> columnInfos;

	/**
	 * 类名
	 */
	private String className;

}
