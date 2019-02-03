package com.qbc.manager.core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.google.common.base.CaseFormat;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.qbc.dto.core.ColumnInfoDTO;
import com.qbc.dto.core.DatabaseInfoDTO;
import com.qbc.dto.core.TableInfoDTO;
import com.qbc.utils.core.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.SneakyThrows;

/**
 * 数据库信息处理，用于获取数据库元信息
 *
 * @author Ma
 */
@Component
public class DatabaseInfoManager {

	private static final String DATABASE_POSTGRE_SQL = "PostgreSQL";

	@Autowired
	private DynamicRoutingDataSource dynamicRoutingDataSource;

	/** 表类型 */
	@AllArgsConstructor
	public enum TableType {

		/** 表 */
		TABLE(new String[] { "TABLE" }),

		/** 视图 */
		VIEW(new String[] { "VIEW" }),

		/** 表和视图 */
		ALL(new String[] { "TABLE", "VIEW" });

		private String[] value;

	}

	/**
	 * 获得数据库所有表和试图信息
	 * 
	 * @return 数据库所有表和试图信息
	 */
	public DatabaseInfoDTO getDatabaseInfoDTO() {
		return getDatabaseInfoDTO(new DatabaseInfoQuery());
	}

	/**
	 * 获得数据库所有表和试图信息
	 * 
	 * @param databaseInfoQuery 条件
	 * @return 数据库所有表和试图信息
	 */
	@SneakyThrows
	public DatabaseInfoDTO getDatabaseInfoDTO(DatabaseInfoQuery databaseInfoQuery) {
		String[] types = databaseInfoQuery.getTableType().value;

		@Cleanup
		Connection connection = dynamicRoutingDataSource.getDataSource(databaseInfoQuery.getDataSourceName())
				.getConnection();
		DatabaseMetaData databaseMetaData = connection.getMetaData();

		// PostgreSQL时，默认不查询系统表
		String schemaPattern = databaseInfoQuery.getSchemaPattern();
		String databaseProductName = databaseMetaData.getDatabaseProductName();
		if (DATABASE_POSTGRE_SQL.equalsIgnoreCase(databaseProductName)) {
			schemaPattern = "public";
		}

		// 获得所有字段信息
		Table<String, String, ColumnInfoDTO> columnInfoTable = getColumnInfoTable(databaseInfoQuery.getCatalog(),
				schemaPattern, databaseInfoQuery.getJdbcTypeMap(), databaseMetaData);

		// 设置主键
		ResultSet primaryKeyResultSet = databaseMetaData.getPrimaryKeys(databaseInfoQuery.getCatalog(), schemaPattern,
				null);
		while (primaryKeyResultSet.next()) {
			String tableName = primaryKeyResultSet.getString("TABLE_NAME");
			String columnName = primaryKeyResultSet.getString("COLUMN_NAME");

			ColumnInfoDTO columnInfoDTO = columnInfoTable.get(tableName, columnName);
			if (columnInfoDTO != null) {
				columnInfoDTO.setKeySeq(primaryKeyResultSet.getShort("KEY_SEQ"));
			}
		}

		// 获得表信息
		List<TableInfoDTO> tableInfos = new ArrayList<>();
		ResultSet tableResultSet = databaseMetaData.getTables(databaseInfoQuery.getCatalog(), schemaPattern,
				databaseInfoQuery.getTableNamePattern(), types);
		while (tableResultSet.next()) {
			String tableName = tableResultSet.getString("TABLE_NAME");

			TableInfoDTO tableInfoDTO = new TableInfoDTO();
			tableInfoDTO.setTableCat(tableResultSet.getString("TABLE_CAT"));
			tableInfoDTO.setTableSchem(tableResultSet.getString("TABLE_SCHEM"));
			tableInfoDTO.setTableName(tableResultSet.getString("TABLE_NAME"));
			tableInfoDTO.setTableType(tableResultSet.getString("TABLE_TYPE"));
			tableInfoDTO.setRemarks(tableResultSet.getString("REMARKS"));
			tableInfoDTO.setUpperCamelTableName(StringUtils.caseFormat(tableInfoDTO.getTableName().toLowerCase(),
					CaseFormat.LOWER_UNDERSCORE, CaseFormat.UPPER_CAMEL));

			// 设置表的字段信息
			List<ColumnInfoDTO> columnInfos = columnInfoTable.row(tableName).values().stream()
					.collect(Collectors.toList());
			tableInfoDTO.setColumnInfos(columnInfos);

			tableInfos.add(tableInfoDTO);
		}

		// 实例化数据库信息实体，并设置数据库信息到实体中。
		DatabaseInfoDTO databaseInfoDTO = new DatabaseInfoDTO();
		databaseInfoDTO.setDatabaseProductName(databaseMetaData.getDatabaseProductName());
		databaseInfoDTO.setDatabaseProductVersion(databaseMetaData.getDatabaseProductVersion());
		databaseInfoDTO.setTableInfos(tableInfos);

		return databaseInfoDTO;
	}

	@SneakyThrows
	private Table<String, String, ColumnInfoDTO> getColumnInfoTable(String catalog, String schemaPattern,
			Map<JDBCType, String> jdbcTypeMap, DatabaseMetaData databaseMetaData) {
		Table<String, String, ColumnInfoDTO> columnInfoTable = HashBasedTable.create();
		ResultSet columnResultSet = databaseMetaData.getColumns(catalog, schemaPattern, null, null);
		while (columnResultSet.next()) {
			String tableName = columnResultSet.getString("TABLE_NAME");
			String columnName = columnResultSet.getString("COLUMN_NAME");

			ColumnInfoDTO columnInfoDTO = new ColumnInfoDTO();
			columnInfoDTO.setColumnName(columnName);
			columnInfoDTO.setDataType(columnResultSet.getInt("DATA_TYPE"));
			columnInfoDTO.setTypeName(columnResultSet.getString("TYPE_NAME"));
			columnInfoDTO.setColumnSize(columnResultSet.getInt("COLUMN_SIZE"));
			columnInfoDTO.setDecimalDigits(columnResultSet.getInt("DECIMAL_DIGITS"));
			columnInfoDTO.setNullable(columnResultSet.getInt("NULLABLE") == 1);
			columnInfoDTO.setRemarks(columnResultSet.getString("REMARKS"));
			columnInfoDTO.setOrdinalPosition(columnResultSet.getInt("ORDINAL_POSITION"));
			columnInfoDTO.setAutoincrement("YES".equals(columnResultSet.getString("IS_AUTOINCREMENT")));
			columnInfoDTO.setLowerCamelColumnName(StringUtils.caseFormat(columnInfoDTO.getColumnName().toLowerCase(),
					CaseFormat.LOWER_UNDERSCORE, CaseFormat.LOWER_CAMEL));
			columnInfoDTO.setJavaType(jdbcTypeMap.get(JDBCType.valueOf(columnInfoDTO.getDataType())));

			columnInfoTable.put(tableName, columnName, columnInfoDTO);
		}
		return columnInfoTable;
	}

}
