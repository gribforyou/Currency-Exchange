package model;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

final class ConnectionManager {
    private static final BasicDataSource DATA_SOURCE = new BasicDataSource();
    private static final String URL_KEY = "db.url";

    static {
        DATA_SOURCE.setUrl(PropertiesUtil.getProperty(URL_KEY));
        DATA_SOURCE.setMaxIdle(5);
    }

    public static Connection getConnection() throws SQLException {
        return DATA_SOURCE.getConnection();
    }
}
