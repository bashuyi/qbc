package com.qbc.core.biz;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.google.common.base.CaseFormat;
import com.qbc.core.biz.DatabaseInfoDTO.ColumnInfo;
import com.qbc.core.biz.DatabaseInfoDTO.TableInfo;
import com.qbc.core.utils.QbcStringUtils;

import lombok.SneakyThrows;

/**
 * 数据库信息处理类
 * 
 * @author Ma
 */
@Component
public class DatabaseInfoManager {

	@Autowired
	private DynamicRoutingDataSource dynamicRoutingDataSource;

	/**
	 * 表类型
	 */
	public enum TableType {

		/**
		 * 表
		 */
		TABLE,

		/**
		 * 视图
		 */
		VIEW

	}

	/**
	 * 获得数据库所有表和试图信息
	 * 
	 * @param ds               数据源名称
	 * @param catalog          类别名称；它必须与存储在数据库中的类别名称匹配；该参数为 "" 表示获取没有类别的那些描述；为null
	 *                         则表示该类别名称不应该用于缩小搜索范围
	 * @param schemaPattern    模式名称的模式； 它必须与存储在数据库中的模式名称匹配； 该参数为 "" 表示获取没有模式的那些描述；
	 *                         为null 则表示该模式名称不应该用于缩小搜索范围
	 * @param tableNamePattern 表名称模式； 它必须与存储在数据库中的表名称匹配
	 * @param tableTypes       要包括的表类型所组成的列表； 为null则表示返回所有类型
	 * @param jdbcTypeMap      java.sql.Types的SQL类型与Java类型的映射关系
	 * @return 数据库所有表和试图信息
	 */
	@SneakyThrows
	public DatabaseInfoDTO getDatabaseInfoBVO(String ds, String catalog, String schemaPattern, String tableNamePattern,
			TableType[] tableTypes, Map<JDBCType, String> jdbcTypeMap) {
		tableTypes = ObjectUtils.defaultIfNull(tableTypes, TableType.values());
		String[] types = Arrays.asList(tableTypes).stream().map(tableType -> tableType.name()).toArray(String[]::new);

		Map<JDBCType, String> defaultJdbcTypeMap = getDefaultJdbcTypeMap();
		jdbcTypeMap = ObjectUtils.defaultIfNull(jdbcTypeMap, new HashMap<>(0));
		defaultJdbcTypeMap.putAll(jdbcTypeMap);

		Connection connection = dynamicRoutingDataSource.getDataSource(ds).getConnection();
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet tableResultSet = databaseMetaData.getTables(catalog, schemaPattern, tableNamePattern, types);
		List<TableInfo> tableInfos = new ArrayList<>();
		while (tableResultSet.next()) {
			TableInfo tableInfo = new TableInfo();
			tableInfo.setTableCat(tableResultSet.getString("TABLE_CAT"));
			tableInfo.setTableSchem(tableResultSet.getString("TABLE_SCHEM"));
			tableInfo.setTableName(tableResultSet.getString("TABLE_NAME"));
			tableInfo.setTableType(tableResultSet.getString("TABLE_TYPE"));
			tableInfo.setRemarks(tableResultSet.getString("REMARKS"));
			tableInfo.setClassName(QbcStringUtils.caseFormat(tableInfo.getTableName().toLowerCase(),
					CaseFormat.LOWER_UNDERSCORE, CaseFormat.UPPER_CAMEL));

			// 获取可在指定类别中使用的表列的描述
			ResultSet columnResultSet = databaseMetaData.getColumns(catalog, schemaPattern, tableInfo.getTableName(),
					null);
			List<ColumnInfo> columnInfos = new ArrayList<>();
			while (columnResultSet.next()) {
				ColumnInfo columnInfo = new ColumnInfo();
				columnInfo.setColumnName(columnResultSet.getString("COLUMN_NAME"));
				columnInfo.setDataType(columnResultSet.getInt("DATA_TYPE"));
				columnInfo.setTypeName(columnResultSet.getString("TYPE_NAME"));
				columnInfo.setColumnSize(columnResultSet.getInt("COLUMN_SIZE"));
				columnInfo.setDecimalDigits(columnResultSet.getInt("DECIMAL_DIGITS"));
				columnInfo.setNullable(columnResultSet.getInt("NULLABLE") == 1);
				columnInfo.setRemarks(columnResultSet.getString("REMARKS"));
				columnInfo.setOrdinalPosition(columnResultSet.getInt("ORDINAL_POSITION"));
				columnInfo.setAutoincrement("YES".equals(columnResultSet.getString("IS_AUTOINCREMENT")));
				columnInfo.setFieldName(QbcStringUtils.caseFormat(columnInfo.getColumnName().toLowerCase(),
						CaseFormat.LOWER_UNDERSCORE, CaseFormat.LOWER_CAMEL));
				columnInfo.setFieldType(defaultJdbcTypeMap.get(JDBCType.valueOf(columnInfo.getDataType())));
				columnInfos.add(columnInfo);
			}

			// 获取对给定表的主键列的描述。
			ResultSet primaryKeyResultSet = databaseMetaData.getPrimaryKeys(catalog, schemaPattern,
					tableInfo.getTableName());
			Map<String, ColumnInfo> columnInfoMap = columnInfos.stream()
					.collect(Collectors.toMap(ColumnInfo::getColumnName, Function.identity()));
			while (primaryKeyResultSet.next()) {
				ColumnInfo columnInfo = columnInfoMap.get(primaryKeyResultSet.getString("COLUMN_NAME"));
				if (columnInfo != null) {
					columnInfo.setKeySeq(primaryKeyResultSet.getShort("KEY_SEQ"));
				}
			}

			tableInfo.setColumnInfos(columnInfos);

			tableInfos.add(tableInfo);
		}

		// 实例化数据库信息实体，并设置数据库信息到实体中。
		DatabaseInfoDTO databaseInfoBVO = new DatabaseInfoDTO();
		databaseInfoBVO.setDatabaseProductName(databaseMetaData.getDatabaseProductName());
		databaseInfoBVO.setDatabaseProductVersion(databaseMetaData.getDatabaseProductVersion());
		databaseInfoBVO.setTableInfos(tableInfos);

		return databaseInfoBVO;
	}

	private Map<JDBCType, String> getDefaultJdbcTypeMap() {
		Map<JDBCType, String> defaultJdbcTypeMap = new HashMap<>(50);
		defaultJdbcTypeMap.put(JDBCType.CHAR, String.class.getSimpleName());
		defaultJdbcTypeMap.put(JDBCType.VARCHAR, String.class.getSimpleName());
		defaultJdbcTypeMap.put(JDBCType.NVARCHAR, String.class.getSimpleName());
		defaultJdbcTypeMap.put(JDBCType.LONGVARCHAR, String.class.getSimpleName());
		defaultJdbcTypeMap.put(JDBCType.NUMERIC, BigDecimal.class.getName());
		defaultJdbcTypeMap.put(JDBCType.DECIMAL, BigDecimal.class.getName());
		defaultJdbcTypeMap.put(JDBCType.BIT, Boolean.class.getSimpleName());
		defaultJdbcTypeMap.put(JDBCType.TINYINT, Integer.class.getSimpleName());
		defaultJdbcTypeMap.put(JDBCType.SMALLINT, Integer.class.getSimpleName());
		defaultJdbcTypeMap.put(JDBCType.INTEGER, Integer.class.getSimpleName());
		defaultJdbcTypeMap.put(JDBCType.BIGINT, Long.class.getSimpleName());
		defaultJdbcTypeMap.put(JDBCType.REAL, Float.class.getSimpleName());
		defaultJdbcTypeMap.put(JDBCType.FLOAT, Double.class.getSimpleName());
		defaultJdbcTypeMap.put(JDBCType.DOUBLE, Double.class.getSimpleName());
		defaultJdbcTypeMap.put(JDBCType.BINARY, byte[].class.getSimpleName());
		defaultJdbcTypeMap.put(JDBCType.VARBINARY, byte[].class.getSimpleName());
		defaultJdbcTypeMap.put(JDBCType.LONGVARBINARY, byte[].class.getSimpleName());
		defaultJdbcTypeMap.put(JDBCType.DATE, LocalDate.class.getName());
		defaultJdbcTypeMap.put(JDBCType.TIME, LocalTime.class.getName());
		defaultJdbcTypeMap.put(JDBCType.TIMESTAMP, LocalDateTime.class.getName());
		defaultJdbcTypeMap.put(JDBCType.TIMESTAMP_WITH_TIMEZONE, OffsetDateTime.class.getName());
		return defaultJdbcTypeMap;
	}

	/**
	 * 获得数据库所有表和试图信息
	 * 
	 * @param ds 数据源名称
	 * @return 数据库所有表和试图信息
	 */
	public DatabaseInfoDTO getDatabaseInfoBVO(String ds) {
		return getDatabaseInfoBVO(ds, null, null, null, null, null);
	}

	/**
	 * 获得数据库所有表和试图信息
	 * 
	 * @return 数据库所有表和试图信息
	 */
	public DatabaseInfoDTO getDatabaseInfoBVO() {
		return getDatabaseInfoBVO(null, null, null, null, null, null);
	}

}
