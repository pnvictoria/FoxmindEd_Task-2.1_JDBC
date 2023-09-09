package dao.сonnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private final Properties properties;
    public ConnectionFactory(Properties properties) {
        this.properties = properties;
    }

    public Connection getConnection() throws SQLException {
        System.out.println("Connection url: " + properties.getProperty("db.url"));
        return DriverManager.getConnection(properties.getProperty("db.url"),
                properties.getProperty("db.user"),
                properties.getProperty("db.password"));
    }
}
