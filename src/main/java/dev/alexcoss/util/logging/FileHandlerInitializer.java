package dev.alexcoss.util.logging;

import dev.alexcoss.util.logging.exceptions.HandlerExceptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.*;


public class FileHandlerInitializer {
    private static final String LOG_DIRECTORY_NAME = "logs";
    private static final Path LOG_DIRECTORY = Paths.get(LOG_DIRECTORY_NAME);

    public static void initializeFileHandler(Logger logger, String logFileName) {
        try {
            createLogDirectoryIfNotExists();

            String pattern = String.format("./%s/%s.log", LOG_DIRECTORY_NAME, logFileName);
            FileHandler fileHandler = new FileHandler(pattern);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            throw new HandlerExceptions(e);
        }
    }

    private static void createLogDirectoryIfNotExists() throws IOException {
        if (!Files.isDirectory(LOG_DIRECTORY)) {
            Files.createDirectories(LOG_DIRECTORY);
        }
    }
}
