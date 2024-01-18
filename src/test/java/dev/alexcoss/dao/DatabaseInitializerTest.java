package dev.alexcoss.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DatabaseInitializerTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ConnectionFactory connectionFactory;

    private DatabaseInitializer databaseInitializer;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

        databaseInitializer = new DatabaseInitializer(connectionFactory);

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @Test
    void shouldInitializeDatabase() throws SQLException {
        databaseInitializer.initializeDatabase();

        verify(connectionFactory).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeUpdate();
        verify(connection).close();
    }
}