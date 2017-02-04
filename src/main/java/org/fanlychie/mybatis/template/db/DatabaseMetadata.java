package org.fanlychie.mybatis.template.db;

import org.fanlychie.mybatis.template.config.Configuration;
import org.fanlychie.mybatis.template.db.metadata.ColumnSchema;
import org.fanlychie.mybatis.template.db.metadata.TableSchema;
import org.fanlychie.mybatis.template.exception.RuntimeCastException;
import org.fanlychie.mybatis.template.schema.Column;
import org.fanlychie.mybatis.template.schema.Table;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 数据库元数据
 * Created by fanlychie on 2017/2/1.
 */
public abstract class DatabaseMetadata {

    /**
     * 获取数据库所有的表
     *
     * @param conn 数据库连接
     * @return 返回数据库所有的表集合
     * @throws Throwable
     */
    protected abstract List<TableSchema> getAllTables(Connection conn) throws Throwable;

    /**
     * 获取表的列集合
     *
     * @param conn      数据库连接
     * @param tableName 表名称
     * @return 返回表的列集合
     * @throws Throwable
     */
    protected abstract List<ColumnSchema> getTableColumns(Connection conn, String tableName) throws Throwable;

    /**
     * 获取 JAVA 对象属性数据类型
     *
     * @param sqlType SQL 数据类型
     * @return 返回映射的类型字符串
     */
    protected abstract String getJavaType(String sqlType);

    /**
     * 获取所有有效的表集合
     *
     * @return 返回所有有效的表集合
     */
    public List<Table> getTables() {
        try {
            List<Table> tables = new ArrayList<>();
            List<TableSchema> tableSchemas = getAllTables(DatabaseConnection.getConnection());
            for (TableSchema tableSchema : tableSchemas) {
                if (!isIgnoreTable(tableSchema.getName())) {
                    Table table = new Table();
                    table.setOrigin(tableSchema.getName());
                    table.setComment(tableSchema.getComment());
                    table.setName(getSpellingTableName(tableSchema.getName()));
                    Column pkColumn = null;
                    List<ColumnSchema> columnSchemas = getTableColumns(DatabaseConnection.getConnection(), tableSchema.getName());
                    List<Column> columns = new ArrayList<>();
                    for (ColumnSchema columnSchema : columnSchemas) {
                        if (!isIgnoreColumn(columnSchema.getName())) {
                            Column column = new Column();
                            column.setOrigin(columnSchema.getName());
                            column.setComment(columnSchema.getComment());
                            column.setName(getSpellingColumnName(columnSchema.getName()));
                            String type = Configuration.getColumn().getTypeMapping().get(columnSchema.getType());
                            if (type != null) {
                                column.setType(type);
                            } else {
                                column.setType(getJavaType(columnSchema.getType()));
                            }
                            if (columnSchema.isPrimaryKey()) {
                                pkColumn = column;
                            } else {
                                columns.add(column);
                            }
                        }
                    }
                    table.setColumns(columns);
                    table.setPrimaryKey(pkColumn);
                    tables.add(table);
                }
            }
            return tables;
        } catch (Throwable e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 是否忽略此表
     *
     * @param tableName 表名称
     * @return 忽略返回 true, 否则返回 false
     */
    private boolean isIgnoreTable(String tableName) {
        for (String escape : Configuration.getTable().getEscapes()) {
            if (tableName.matches(escape)) {
                return false;
            }
        }
        for (String ignore : Configuration.getTable().getIgnores()) {
            if (tableName.matches(ignore)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否忽略此列
     *
     * @param columnName 列名称
     * @return 忽略返回 true, 否则返回 false
     */
    private boolean isIgnoreColumn(String columnName) {
        for (String escape : Configuration.getColumn().getEscapes()) {
            if (columnName.matches(escape)) {
                return false;
            }
        }
        for (String ignore : Configuration.getColumn().getIgnores()) {
            if (columnName.matches(ignore)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取驼峰拼写法的表名
     *
     * @param tableName 表名称
     * @return 返回驼峰拼写法的表名
     */
    private String getSpellingTableName(String tableName) {
        tableName = ignorePrefixName(Configuration.getTable().getPrefixs(), tableName);
        return capitalize(tableName, Configuration.getTable().getSeparator(), true);
    }

    /**
     * 获取驼峰拼写法的列名
     *
     * @param columnName 列名称
     * @return 返回驼峰拼写法的列名
     */
    private String getSpellingColumnName(String columnName) {
        columnName = ignorePrefixName(Configuration.getColumn().getPrefixs(), columnName);
        return capitalize(columnName, Configuration.getColumn().getSeparator(), false);
    }

    /**
     * 忽略前缀名称
     *
     * @param prefixs 前缀集合
     * @param source  源字符
     * @return 返回忽略前缀的字符串
     */
    private String ignorePrefixName(Set<String> prefixs, String source) {
        String tablePrefix = "";
        for (String prefix : prefixs) {
            if (source.startsWith(prefix) && prefix.length() > tablePrefix.length()) {
                tablePrefix = prefix;
            }
        }
        if (!tablePrefix.isEmpty()) {
            source = source.replace(tablePrefix, "");
        }
        return source;
    }

    /**
     * 在遇到指定的分隔符时, 去掉分隔符并将分隔符后的第一个字母大写
     *
     * @param source           字符串
     * @param separator        分隔符
     * @param initialUpperCase 是否将字符串的首字母大写
     * @return
     */
    private String capitalize(String source, String separator, boolean initialUpperCase) {
        source = source.toLowerCase();
        if (separator != null && source.contains(separator)) {
            int index = 0;
            String target = "";
            String[] sources = source.split(separator);
            if (!initialUpperCase) {
                index = 1;
                target = sources[0];
            }
            for (int i = index; i < sources.length; i++) {
                target += capitalize(sources[i]);
            }
            return target;
        } else {
            return initialUpperCase ? capitalize(source) : source;
        }
    }

    /**
     * 首字母大写
     *
     * @param source 字符串
     * @return
     */
    private String capitalize(String source) {
        if (source.length() == 1) {
            return source.toUpperCase();
        }
        return Character.toUpperCase(source.charAt(0)) + source.substring(1);
    }

}