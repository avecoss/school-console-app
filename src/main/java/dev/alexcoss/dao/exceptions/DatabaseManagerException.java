package dev.alexcoss.dao.exceptions;

public class DatabaseManagerException extends RuntimeException {
    public DatabaseManagerException(String message) {
        super(message);
    }

    public DatabaseManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
