package dev.alexcoss.dao;

import dev.alexcoss.util.JdbcPropertiesReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSqlConnectionFactory implements ConnectionFactory {

    private final String url;
    private final String username;
    private final String password;

    public PostgreSqlConnectionFactory() {
        JdbcPropertiesReader reader = new JdbcPropertiesReader();
        this.url = reader.getJdbcUrl();
        this.username = reader.getJdbcUsername();
        this.password = reader.getJdbcPassword();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}