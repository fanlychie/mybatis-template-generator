package org.fanlychie.mybatis.schema;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * 元数据
 * 
 * @author fanlychie
 */
public class SchemaMetaData {

	private Schema schema;

	private Connection conn;

	private DatabaseMetaData metaData;

	public SchemaMetaData(Schema schema, Connection conn) {
		this.conn = conn;
		this.schema = schema;
	}

	/**
	 * 获取表的集合
	 * 
	 * @return List
	 */
	public List<Table> getTables() throws Throwable {
		metaData = conn.getMetaData();
		List<Table> tables = new ArrayList<>();
		try (ResultSet rs = metaData.getTables(null, "%", "%", new String[] { "TABLE" })) {
			String ignorePattern = schema.getTableIgnores();
			Set<String> ignoreEscapes = schema.getTableEscapes();
			while (rs.next()) {
				String tablename = rs.getString("TABLE_NAME");
				// 忽略不处理的表
				if (StringUtils.isNotBlank(ignorePattern) && tablename.matches(ignorePattern)
						&& !ignoreEscapes.contains(tablename)) {
					continue;
				}
				tables.add(buildTable(tablename));
			}
			Collections.sort(tables);
			return tables;
		}
	}

	/**
	 * 构建表模型
	 * 
	 * @param tablename
	 *            表名
	 * @return Table
	 */
	private Table buildTable(String tablename) throws Throwable {
		Table table = new Table();
		table.columnOrigin = new HashMap<String, String>();
		String separator = schema.getTableNameSeparator();
		// 表名进行驼峰标识拼写
		String name = capitalize(processTableName(tablename), separator);
		table.setName(name);
		table.setOrigin(tablename);
		table.setColumns(getColumns(table));
		table.setOpk(getPrimaryKey(tablename));
		table.setPk(capitalize(table.getOpk(), schema.getColumnNameSeparator(), false));
		for (Column column : table.getColumns()) {
			if (column.isPrimaryKey()) {
				table.setPkType(column.getType());
				break;
			}
		}
		Collections.sort(table.getColumns());
		return table;
	}

	/**
	 * 获取列的集合
	 * 
	 * @param table
	 *            表
	 * @return
	 * @throws Throwable
	 */
	private List<Column> getColumns(Table table) throws Throwable {
		List<Column> columns = new ArrayList<>();
		try (ResultSet rs = metaData.getColumns(null, null, table.getOrigin(), null)) {
			try (Statement statement = conn.createStatement()) {
				ResultSetMetaData rsmd = statement.executeQuery("SELECT * FROM " + table.getOrigin()).getMetaData();
				int count = 1;
				while (rs.next()) {
					String name = rs.getString("COLUMN_NAME");
					// 忽略不处理的列
					if (schema.getColumnIgnores().contains(name)) {
						count++;
						continue;
					}
					columns.add(buildColumn(table, name, rs.getString("REMARKS"), rs.getInt("DATA_TYPE"),
							rsmd.getColumnTypeName(count++)));
				}
				return columns;
			}
		}
	}

	/**
	 * 构建列模型
	 * 
	 * @param table
	 *            表模型
	 * @param name
	 *            列名称
	 * @param remark
	 *            列备注
	 * @param type
	 *            列类型
	 * @param typeName
	 *            列类型名称
	 * @return Column
	 */
	private Column buildColumn(Table table, String name, String remark, int type, String typeName) {
		Column column = new Column(table);
		String separator = schema.getColumnNameSeparator();
		// 列名进行驼峰标识拼写
		String columnName = capitalize(name, separator, false);
		table.columnOrigin.put(columnName, name);
		column.setName(columnName);
		column.setRemark(remark);
		typeName = typeName.toLowerCase();
		// 处理自定义映射的数据类型
		if (schema.getDataTypes().containsKey(typeName)) {
			column.setType(schema.getDataTypes().get(typeName));
		} else {
			column.setType(getTypeName(type));
		}
		return column;
	}

	/**
	 * 获取主键
	 * 
	 * @param tablename
	 *            表名称
	 * @return
	 * @throws Throwable
	 */
	private String getPrimaryKey(String tablename) throws Throwable {
		try (ResultSet rs = metaData.getPrimaryKeys(null, null, tablename)) {
			if (rs.next()) {
				return rs.getString("COLUMN_NAME");
			}
			return null;
		}
	}

	/**
	 * 首字母大写, 在遇到指定的分隔符时, 去掉分隔符并将分隔符后的第一个字母大写
	 * 
	 * @param str
	 *            字符串
	 * @param separator
	 *            分隔符
	 * @return
	 */
	private String capitalize(String str, String separator) {
		return capitalize(str, separator, true);
	}

	/**
	 * 在遇到指定的分隔符时, 去掉分隔符并将分隔符后的第一个字母大写
	 * 
	 * @param str
	 *            字符串
	 * @param separator
	 *            分隔符
	 * @param initialUpperCase
	 *            是否将字符串的首字母大写
	 * @return
	 */
	private String capitalize(String str, String separator, boolean initialUpperCase) {
		if (str == null) {
			return null;
		}
		if (separator != null && str.contains(separator)) {
			int index = 0;
			String target = "";
			String[] sources = str.split(separator);
			if (!initialUpperCase) {
				index = 1;
				target = sources[0].toLowerCase();
			}
			for (int i = index; i < sources.length; i++) {
				target += capitalize(sources[i]);
			}
			return target;
		} else {
			return initialUpperCase ? capitalize(str) : str.toLowerCase();
		}
	}

	/**
	 * 首字母大写
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	private String capitalize(String str) {
		if (!isBlank(str)) {
			char first = str.charAt(0);
			if (!Character.isTitleCase(first)) {
				return Character.toUpperCase(first) + str.substring(1);
			}
		}
		return str;
	}

	private boolean isBlank(String str) {
		if (str != null && !str.isEmpty()) {
			int len = str.length();
			for (int i = 0; i < len; i++) {
				if (!Character.isWhitespace(str.charAt(i))) {
					return false;
				}
			}
		}
		return true;
	}
	
	private String processTableName(String tablename) {
		Set<String> tablePrefix = schema.getTablePrefix();
		String prefixName = "";
		for (String prefix : tablePrefix) {
			if (tablename.startsWith(prefix) && prefix.length() > prefixName.length()) {
				prefixName = prefix;
			}
		}
		if (!prefixName.isEmpty()) {
			return tablename.replace(prefixName, "");
		}
		return tablename;
	}

	/**
	 * 获取类型名称
	 * 
	 * @param type
	 *            类型值
	 * @return
	 */
	private String getTypeName(int type) {
		switch (type) {
		case Types.BIT:
		case Types.BOOLEAN:
			return "Boolean";
		case Types.TINYINT:
		case Types.SMALLINT:
			return "Integer";
		case Types.INTEGER:
			return "Long";
		case Types.BIGINT:
			return "java.math.BigInteger";
		case Types.DECIMAL:
		case Types.NUMERIC:
			return "java.math.BigDecimal";
		case Types.REAL:
			return "Float";
		case Types.FLOAT:
		case Types.DOUBLE:
			return "Double";
		case Types.CHAR:
		case Types.VARCHAR:
		case Types.LONGVARCHAR:
		case Types.BINARY:
		case Types.VARBINARY:
		case Types.LONGVARBINARY:
			return "String";
		case Types.DATE:
		case Types.TIME:
		case Types.TIMESTAMP:
			return "java.util.Date";
		default:
			return "Object";
		}
	}

}