package dev.alexcoss.util;

import dev.alexcoss.util.exceptions.JdbcPropertiesReaderException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JdbcPropertiesReader {
    private static final String PROPERTIES_PATH = "application.properties";
    private static final String DATABASE_URL = "database.url";
    private static final String DATABASE_USERNAME = "database.username";
    private static final String DATABASE_PASSWORD = "database.password";
    private static final String DATABASE_DRIVER = "database.driver";

    private final Properties properties = new Properties();

    public JdbcPropertiesReader() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(PROPERTIES_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            throw new JdbcPropertiesReaderException("Unable to read file " + PROPERTIES_PATH, e);
        }
    }

    public String getJdbcUrl() {
        return properties.getProperty(DATABASE_URL);
    }

    public String getJdbcUsername() {
        return properties.getProperty(DATABASE_USERNAME);
    }

    public String getJdbcPassword() {
        return properties.getProperty(DATABASE_PASSWORD);
    }

    private String getJdbcDriver() {
        return properties.getProperty(DATABASE_DRIVER);
    }

    private void loadJdbcDriver() {
        try {
            Class.forName(getJdbcDriver());
        } catch (ClassNotFoundException e) {
            throw new JdbcPropertiesReaderException("Error loading JDBC driver ", e);
        }
    }
}
