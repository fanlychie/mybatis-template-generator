package org.fanlychie.mybatis.template.db;

import org.fanlychie.mybatis.template.db.metadata.ColumnSchema;
import org.fanlychie.mybatis.template.db.metadata.TableSchema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * MYSQL 实现
 * Created by fanlychie on 2017/2/1.
 */
public class MySQLMetadata extends DatabaseMetadata {

    @Override
    protected List<TableSchema> getAllTables(Connection conn) throws Throwable {
        String sql = "SELECT TABLE_NAME, TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, conn.getCatalog());
        List<TableSchema> tables = new ArrayList<>();
        try (ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                tables.add(new TableSchema(rs.getString("TABLE_NAME"), rs.getString("TABLE_COMMENT")));
            }
        }
        return tables;
    }

    @Override
    protected List<ColumnSchema> getTableColumns(Connection conn, String tableName) throws Throwable {
        String sql = "SELECT COLUMN_NAME, DATA_TYPE, COLUMN_KEY, COLUMN_COMMENT FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, conn.getCatalog());
        statement.setString(2, tableName);
        List<ColumnSchema> columns = new ArrayList<>();
        try (ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                columns.add(new ColumnSchema(
                        rs.getString("COLUMN_NAME"),
                        rs.getString("DATA_TYPE"),
                        rs.getString("COLUMN_COMMENT"), "PRI".equals(
                        rs.getString("COLUMN_KEY"))));
            }
        }
        return columns;
    }

    @Override
    protected String getJavaType(String sqlType) {
        switch (sqlType.toLowerCase()) {
            case "set":
            case "char":
            case "text":
            case "enum":
            case "varchar":
            case "tinytext":
            case "longtext":
            case "mediumtext":
                return "String";
            case "blob":
            case "binary":
            case "varbinary":
            case "tinyblob":
            case "longblob":
            case "mediumblob":
                return "byte[]";
            case "int":
            case "integer":
            case "tinyint":
            case "smallint":
            case "mediumint":
                return "Integer";
            case "bigint":
                return "Long";
            case "bit":
                return "Boolean";
            case "real":
            case "float":
                return "Float";
            case "double":
                return "Double";
            case "decimal":
            case "numeric":
                return "java.math.BigDecimal";
            case "date":
            case "time":
            case "datetime":
            case "timestamp":
                return "java.util.Date";
            default:
                return "Object";
        }
    }

}