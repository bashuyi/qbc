package com.qbc.manager.core;

import java.math.BigDecimal;
import java.sql.JDBCType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import com.qbc.manager.core.DatabaseInfoManager.TableType;

import lombok.Data;

/**
 * 数据库信息条件
 *
 * @author Ma
 */
@Data
public class DatabaseInfoQuery {

	/**
	 * 数据源名称
	 */
	private String dataSourceName;

	/**
	 * 类别名称；它必须与存储在数据库中的类别名称匹配
	 */
	private String catalog;

	/**
	 * 模式名称； 它必须与存储在数据库中的模式名称匹配
	 */
	private String schemaPattern;

	/**
	 * 表名称； 它必须与存储在数据库中的表名称匹配
	 */
	private String tableNamePattern;

	/**
	 * 表类型
	 */
	private TableType tableType = TableType.TABLE;

	/**
	 * java.sql.Types的SQL类型与Java类型的映射关系
	 */
	private Map<JDBCType, String> jdbcTypeMap;

	public DatabaseInfoQuery() {
		jdbcTypeMap = new HashMap<>(50);
		jdbcTypeMap.put(JDBCType.CHAR, String.class.getSimpleName());
		jdbcTypeMap.put(JDBCType.VARCHAR, String.class.getSimpleName());
		jdbcTypeMap.put(JDBCType.NVARCHAR, String.class.getSimpleName());
		jdbcTypeMap.put(JDBCType.LONGVARCHAR, String.class.getSimpleName());
		jdbcTypeMap.put(JDBCType.NUMERIC, BigDecimal.class.getName());
		jdbcTypeMap.put(JDBCType.DECIMAL, BigDecimal.class.getName());
		jdbcTypeMap.put(JDBCType.BIT, Boolean.class.getSimpleName());
		jdbcTypeMap.put(JDBCType.TINYINT, Integer.class.getSimpleName());
		jdbcTypeMap.put(JDBCType.SMALLINT, Integer.class.getSimpleName());
		jdbcTypeMap.put(JDBCType.INTEGER, Integer.class.getSimpleName());
		jdbcTypeMap.put(JDBCType.BIGINT, Long.class.getSimpleName());
		jdbcTypeMap.put(JDBCType.REAL, Float.class.getSimpleName());
		jdbcTypeMap.put(JDBCType.FLOAT, Double.class.getSimpleName());
		jdbcTypeMap.put(JDBCType.DOUBLE, Double.class.getSimpleName());
		jdbcTypeMap.put(JDBCType.BINARY, byte[].class.getSimpleName());
		jdbcTypeMap.put(JDBCType.VARBINARY, byte[].class.getSimpleName());
		jdbcTypeMap.put(JDBCType.LONGVARBINARY, byte[].class.getSimpleName());
		jdbcTypeMap.put(JDBCType.DATE, LocalDate.class.getName());
		jdbcTypeMap.put(JDBCType.TIME, LocalTime.class.getName());
		jdbcTypeMap.put(JDBCType.TIMESTAMP, LocalDateTime.class.getName());
		jdbcTypeMap.put(JDBCType.TIMESTAMP_WITH_TIMEZONE, OffsetDateTime.class.getName());
	}

}
