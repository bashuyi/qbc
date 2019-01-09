package com.qbc.manager.core;

import java.util.function.Supplier;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.dynamic.datasource.DynamicDataSourceCreator;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;

@Component
public class DataSourceManager {

	@Autowired
	private DynamicRoutingDataSource dynamicRoutingDataSource;

	@Autowired
	private DynamicDataSourceCreator dynamicDataSourceCreator;

	/**
	 * 在指定数据源执行操作
	 * 
	 * @param dataSourceName 数据源名称
	 * @param supplier       要执行的操作
	 * @return 操作的返回值
	 */
	@DS("#dataSourceName")
	public <T> T excute(String dataSourceName, Supplier<T> supplier) {
		return supplier.get();
	}

	/**
	 * 在指定数据源执行操作
	 * 
	 * @param dataSourceName 数据源名称
	 */
	@DS("#dataSourceName")
	public void excute(String dataSourceName, Runnable runnable) {
		runnable.run();
	}

	/**
	 * 添加数据源
	 * 
	 * @param dataSourceName     数据源名称
	 * @param dataSourceProperty 数据源属性
	 */
	public void addDataSource(String dataSourceName, DataSourceProperty dataSourceProperty) {
		DataSource dataSource = dynamicDataSourceCreator.createDataSource(dataSourceProperty);
		dynamicRoutingDataSource.addDataSource(dataSourceName, dataSource);
	}

	/**
	 * 移除数据源
	 * 
	 * @param dataSourceName 数据源名称
	 */
	public void removeDataSource(String dataSourceName) {
		dynamicRoutingDataSource.removeDataSource(dataSourceName);
	}

	/**
	 * 获得数据源
	 * 
	 * @param dataSourceName 数据源名称
	 * @return 数据源
	 */
	public DataSource getDataSource(String dataSourceName) {
		return dynamicRoutingDataSource.getDataSource(dataSourceName);
	}

}
