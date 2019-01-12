package com.qbc.dto.core;

import lombok.Data;

/**
 * 列信息的传输对象
 *
 * @author Ma
 */
@Data
public class ColumnInfoDTO {

	/**
	 * 列名称
	 */
	private String columnName;

	/**
	 * 来自 java.sql.Types 的 SQL 类型
	 */
	private Integer dataType;

	/**
	 * 数据源依赖的类型名称，对于 UDT，该类型名称是完全限定的
	 */
	private String typeName;

	/**
	 * 列的大小。
	 */
	private Integer columnSize;

	/**
	 * 小数部分的位数。对于 DECIMAL_DIGITS 不适用的数据类型，则返回 Null。
	 */
	private Integer decimalDigits;

	/**
	 * 是否允许使用 NULL。
	 */
	private Boolean nullable;

	/**
	 * 描述列的注释（可为 null）
	 */
	private String remarks;

	/**
	 * 表中的列的索引（从 1 开始）
	 */
	private Integer ordinalPosition;

	/**
	 * 指示此列是否自动增加
	 */
	private Boolean autoincrement;

	/**
	 * 首字母小写驼峰形式列名
	 */
	private String lowerCamelColumnName;

	/**
	 * 字段类型
	 */
	private String javaType;

	/**
	 * 主键序号
	 */
	private Short keySeq;

}
