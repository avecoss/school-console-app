package dev.alexcoss.dao;

import dev.alexcoss.dao.exceptions.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;

public abstract class AbstractDao<T, E> extends AbstractDatabaseManager implements Dao<T, E> {

    public AbstractDao(String loggerName, ConnectionFactory connectionFactory) {
        super(loggerName, connectionFactory);
    }

    protected abstract T resultSetToObject(ResultSet resultSet) throws SQLException;

    protected void handleSQLException(SQLException e, String message, String sql, Object... params) {
        String fullMessage = String.format("%s\nSQL: %s\nParameters: %s", message, sql, Arrays.toString(params));
        logger.log(Level.SEVERE, fullMessage, e);
    }
}
