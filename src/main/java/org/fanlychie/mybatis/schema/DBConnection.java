package org.fanlychie.mybatis.schema;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据库连接
 * 
 * @author fanlychie
 */
public class DBConnection {

	public static Connection getConnection(DataSource dataSource) {
		try {
			Class.forName(dataSource.getDriverClass());
			return DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

}