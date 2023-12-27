package dev.alexcoss.dao;

import dev.alexcoss.dao.exceptions.DatabaseManagerException;
import dev.alexcoss.util.logging.FileHandlerInitializer;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DatabaseManager {

    protected final ConnectionFactory connectionFactory;
    protected final Logger logger;

    public DatabaseManager(String loggerName) {
        this.connectionFactory = new PostgreSqlConnectionFactory();
        this.logger = initializeLogger(loggerName);
    }

    protected void handleSQLException(SQLException e, String message) {
        logSQLException(Level.SEVERE, e, message, null);
        throw new DatabaseManagerException(buildFullMessage(message, null), e);
    }

    protected void handleSQLException(SQLException e, String message, String sql) {
        logSQLException(Level.SEVERE, e, message, sql);
        throw new DatabaseManagerException(buildFullMessage(message, sql), e);
    }

    private Logger initializeLogger(String loggerName) {
        Logger logger = Logger.getLogger(loggerName);
        FileHandlerInitializer.initializeFileHandler(logger, loggerName);
        return logger;
    }

    private void logSQLException(Level level, SQLException e, String message, String sql) {
        String fullMessage = buildFullMessage(message, sql);
        logger.log(level, fullMessage, e);
    }

    private String buildFullMessage(String message, String sql) {
        StringBuilder fullMessage = new StringBuilder(message);
        if (sql != null) {
            fullMessage.append("\nSQL: ").append(sql);
        }
        return fullMessage.toString();
    }
}
