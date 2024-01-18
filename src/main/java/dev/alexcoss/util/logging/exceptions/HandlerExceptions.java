package dev.alexcoss.util.logging.exceptions;

public class HandlerExceptions extends RuntimeException{
    public HandlerExceptions(String message) {
        super(message);
    }

    public HandlerExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public HandlerExceptions(Throwable cause) {
        super(cause);
    }
}
