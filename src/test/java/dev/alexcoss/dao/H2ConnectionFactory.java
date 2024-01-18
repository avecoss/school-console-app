package dev.alexcoss.dao;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class H2ConnectionFactory implements ConnectionFactory {
    private final JdbcDataSource dataSource;

    public H2ConnectionFactory(JdbcDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
