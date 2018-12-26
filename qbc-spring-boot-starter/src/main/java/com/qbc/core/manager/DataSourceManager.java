package com.qbc.core.manager;

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
	 * @param ds
	 *            数据源名称
	 * @param supplier
	 *            要执行的操作
	 * @return 操作的返回值
	 */
	@DS("#ds")
	public <T> T excute(String ds, Supplier<T> supplier) {
		return supplier.get();
	}

	/**
	 * 在指定数据源执行操作
	 * 
	 * @param ds
	 *            数据源名称
	 */
	@DS("#ds")
	public void excute(String ds, Runnable runnable) {
		runnable.run();
	}

	/**
	 * 添加数据源
	 * 
	 * @param ds
	 *            数据源名称
	 * @param dataSourceProperty
	 *            数据源属性
	 */
	public void addDataSource(String ds, DataSourceProperty dataSourceProperty) {
		DataSource dataSource = dynamicDataSourceCreator.createDataSource(dataSourceProperty);
		dynamicRoutingDataSource.addDataSource(ds, dataSource);
	}

	/**
	 * 移除数据源
	 * 
	 * @param ds
	 *            数据源名称
	 */
	public void removeDataSource(String ds) {
		dynamicRoutingDataSource.removeDataSource(ds);
	}

	/**
	 * 获得数据源
	 * 
	 * @param ds
	 *            数据源名称
	 * @return 数据源
	 */
	public DataSource getDataSource(String ds) {
		return dynamicRoutingDataSource.getDataSource(ds);
	}

}
