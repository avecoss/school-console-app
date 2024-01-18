package dev.alexcoss.dao;

import dev.alexcoss.util.FileReader;

import java.sql.*;

public class DatabaseInitializer extends AbstractDatabaseManager implements Initializer {

    private static final String SQL_PATH_CREATE_TABLES = "src/main/resources/sql/create_tables.sql";

    public DatabaseInitializer(ConnectionFactory connectionFactory) {
        super(DatabaseInitializer.class.getName(), connectionFactory);
    }

    @Override
    public void initializeDatabase() {
        try (Connection connection = connectionFactory.getConnection()) {
            executeSqlScript(connection);
        } catch (SQLException e) {
            handleSQLException(e, "Error initializing database");
        }
    }

    private void executeSqlScript(Connection connection) throws SQLException {
        String sqlScript = readSqlScript();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlScript)) {
            preparedStatement.executeUpdate();
            logger.info("Executed SQL script: " + sqlScript);
        } catch (SQLException e) {
            handleSQLException(e, "Error executing SQL script", sqlScript);
        }
    }

    private String readSqlScript() {
        FileReader fileReader = new FileReader();

        return fileReader.fileRead(SQL_PATH_CREATE_TABLES, bufferedReader -> {
            StringBuilder result = new StringBuilder();
            bufferedReader.lines().forEach(result::append);
            return result.toString();
        });
    }
}
