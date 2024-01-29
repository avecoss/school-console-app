package dev.alexcoss.dao;

import dev.alexcoss.util.logging.FileHandlerInitializer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractDao<T, E> implements Dao<T, E> {

    protected final JdbcTemplate jdbcTemplate;
    protected final Logger logger;

    public AbstractDao(String loggerName, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.logger = initializeLogger(loggerName);
    }

    protected void handleSQLException(DataAccessException e, String message, String sql, Object... params) {
        String fullMessage = String.format("%s\nSQL: %s\nParameters: %s", message, sql, Arrays.toString(params));
        logger.log(Level.SEVERE, fullMessage, e);
    }

    private Logger initializeLogger(String loggerName) {
        Logger logger = Logger.getLogger(loggerName);
        FileHandlerInitializer.initializeFileHandler(logger, loggerName);
        return logger;
    }
}
