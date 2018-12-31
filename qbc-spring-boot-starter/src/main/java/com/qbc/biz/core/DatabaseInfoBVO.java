package com.qbc.biz.core;

import java.util.List;

import lombok.Data;

@Data
public class DatabaseInfoBVO {

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
	private List<TableInfo> tableInfos;

	/**
	 * 表信息实体
	 */
	@Data
	public static class TableInfo {

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
		private List<ColumnInfo> columnInfos;

		/**
		 * 类名
		 */
		private String className;

	}

	/**
	 * 列信息实体
	 */
	@Data
	public static class ColumnInfo {

		/**
		 * 列名称
		 */
		private String columnName;

		/**
		 * 来自 java.sql.Types 的 SQL 类型
		 */
		private int dataType;

		/**
		 * 数据源依赖的类型名称，对于 UDT，该类型名称是完全限定的
		 */
		private String typeName;

		/**
		 * 列的大小。
		 */
		private int columnSize;

		/**
		 * 小数部分的位数。对于 DECIMAL_DIGITS 不适用的数据类型，则返回 Null。
		 */
		private int decimalDigits;

		/**
		 * 是否允许使用 NULL。
		 */
		private boolean nullable;

		/**
		 * 描述列的注释（可为 null）
		 */
		private String remarks;

		/**
		 * 表中的列的索引（从 1 开始）
		 */
		private int ordinalPosition;

		/**
		 * 指示此列是否自动增加
		 */
		private boolean autoincrement;

		/**
		 * 字段名
		 */
		private String fieldName;

		/**
		 * 字段类型
		 */
		private String fieldType;

		/**
		 * 主键序号
		 */
		private Short keySeq;

	}

}
