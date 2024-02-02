package dev.alexcoss.service;

import dev.alexcoss.util.logging.FileHandlerInitializer;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractService {

    protected final Logger logger;

    public AbstractService(String loggerName) {
        this.logger = initializeLogger(loggerName);
    }

    protected void handleServiceException(Exception e, String message) {
        logger.log(Level.SEVERE, message, e);
    }

    protected void handleServiceException(String message) {
        logger.log(Level.SEVERE, message);
    }

    private Logger initializeLogger(String loggerName) {
        Logger logger = Logger.getLogger(loggerName);
        FileHandlerInitializer.initializeFileHandler(logger, loggerName);
        return logger;
    }
}
