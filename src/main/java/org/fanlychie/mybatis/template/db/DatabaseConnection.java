package org.fanlychie.mybatis.template.db;

import org.fanlychie.mybatis.template.config.Configuration;
import org.fanlychie.mybatis.template.config.Datasource;
import org.fanlychie.mybatis.template.exception.RuntimeCastException;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据库连接
 * Created by fanlychie on 2017/2/1.
 */
public class DatabaseConnection {

    /**
     * 获取数据库连接
     *
     * @return 返回数据库连接对象
     */
    public static Connection getConnection() {
        try {
            Datasource datasource = Configuration.getDatasource();
            Class.forName(datasource.getDriverClass());
            return DriverManager.getConnection(datasource.getUrl(), datasource.getUsername(), datasource.getPassword());
        } catch (Throwable e) {
            throw new RuntimeCastException(e);
        }
    }

}