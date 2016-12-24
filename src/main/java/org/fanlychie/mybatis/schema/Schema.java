package org.fanlychie.mybatis.schema;

import java.util.Map;
import java.util.Set;

/**
 * 数据库模型
 * 
 * @author fanlychie
 */
public class Schema {

	// 表名分隔符
	private String tableNameSeparator;

	// 列名分隔符
	private String columnNameSeparator;

	// 忽略模式匹配的表名
	private String tableIgnores;

	// 逃逸模式匹配的表名
	private Set<String> tableEscapes;

	// 忽略指定的列名
	private Set<String> columnIgnores;

	// 数据类型映射表
	private Map<String, String> dataTypes;
	
	// 表的前缀名
	private Set<String> tablePrefix;

	public String getTableNameSeparator() {
		return tableNameSeparator;
	}

	public void setTableNameSeparator(String tableNameSeparator) {
		this.tableNameSeparator = tableNameSeparator;
	}

	public String getColumnNameSeparator() {
		return columnNameSeparator;
	}

	public void setColumnNameSeparator(String columnNameSeparator) {
		this.columnNameSeparator = columnNameSeparator;
	}

	public String getTableIgnores() {
		return tableIgnores;
	}

	public void setTableIgnores(String tableIgnores) {
		this.tableIgnores = tableIgnores;
	}

	public Set<String> getTableEscapes() {
		return tableEscapes;
	}

	public void setTableEscapes(Set<String> tableEscapes) {
		this.tableEscapes = tableEscapes;
	}

	public Set<String> getColumnIgnores() {
		return columnIgnores;
	}

	public void setColumnIgnores(Set<String> columnIgnores) {
		this.columnIgnores = columnIgnores;
	}

	public Map<String, String> getDataTypes() {
		return dataTypes;
	}

	public void setDataTypes(Map<String, String> dataTypes) {
		this.dataTypes = dataTypes;
	}

	public Set<String> getTablePrefix() {
		return tablePrefix;
	}

	public void setTablePrefix(Set<String> tablePrefix) {
		this.tablePrefix = tablePrefix;
	}

	@Override
	public String toString() {
		return "Schema [tableNameSeparator=" + tableNameSeparator + ", columnNameSeparator=" + columnNameSeparator
				+ ", tableIgnores=" + tableIgnores + ", tableEscapes=" + tableEscapes + ", columnIgnores="
				+ columnIgnores + ", dataTypes=" + dataTypes + ", tablePrefix=" + tablePrefix + "]";
	}

}