package dev.alexcoss.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DatabaseRemoverTest {
    @Mock
    private Connection connection;
    @Mock
    private Statement statement;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private DatabaseMetaData metaData;
    @Mock
    private ResultSet resultSet;
    @Mock
    private ConnectionFactory connectionFactory;

    private DatabaseRemover databaseRemover;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

        databaseRemover = new DatabaseRemover(connectionFactory);

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.getMetaData()).thenReturn(metaData);
        when(metaData.getTables(null, null, "%", new String[]{"TABLE"})).thenReturn(resultSet);
    }


    @Test
    void shouldRemoveDatabase() throws SQLException {
        databaseRemover.removeDatabase();

        verify(connectionFactory).getConnection();
        verify(connection).getMetaData();
        verify(metaData).getTables(null, null, "%", new String[]{"TABLE"});
        verify(connection).createStatement();
        verify(statement).close();
        verify(connection).close();

    }
}