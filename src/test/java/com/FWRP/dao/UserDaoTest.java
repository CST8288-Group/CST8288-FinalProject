package com.FWRP.dao;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.AfterEach;

import com.FWRP.dto.UserDTO;
import com.FWRP.utils.DBConnection;

import jakarta.servlet.ServletContext;

@ExtendWith(MockitoExtension.class)
public class UserDaoTest {

    @Mock
    private ServletContext context;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private UserDao userDao;

    private MockedStatic<DBConnection> mockedDBConnection;

    @BeforeEach
    public void setUp() throws SQLException {
        // Ensure DBConnection.getConnection(context) returns a mock Connection
        mockedDBConnection = Mockito.mockStatic(DBConnection.class);
        mockedDBConnection.when(() -> DBConnection.getConnection(context)).thenReturn(connection);
        
        // Ensure connection.prepareStatement() returns a mock PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @Test
    public void testRegisterUser() throws SQLException {
        UserDTO user = new UserDTO();
        user.setName("John Doe");
        user.setPassword("password");
        user.setEmail("john.doe@example.com");
        user.setType(1);
        user.setAddress("123 Main St");
        user.setVerificationToken("token123");

        userDao.registerUser(user);

        verify(preparedStatement, times(1)).setString(1, "John Doe");
        verify(preparedStatement, times(1)).setString(2, "password");
        verify(preparedStatement, times(1)).setString(3, "john.doe@example.com");
        verify(preparedStatement, times(1)).setInt(4, 1);
        verify(preparedStatement, times(1)).setString(5, "123 Main St");
        verify(preparedStatement, times(1)).setString(6, "token123");
        verify(preparedStatement, times(1)).setBoolean(7, false);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testVerifyUser() throws SQLException {
        UserDTO user = new UserDTO();
        user.setId(1L);

        userDao.verifyUser(user);

        verify(preparedStatement, times(1)).setLong(1, 1L);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testFindByVerificationToken() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("John Doe");
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getString("email")).thenReturn("john.doe@example.com");
        when(resultSet.getInt("type")).thenReturn(1);
        when(resultSet.getString("address")).thenReturn("123 Main St");
        when(resultSet.getString("verification_token")).thenReturn("token123");
        when(resultSet.getBoolean("verified")).thenReturn(false);

        UserDTO user = userDao.findByVerificationToken("token123");

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("password", user.getPassword());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals(1, user.getType());
        assertEquals("123 Main St", user.getAddress());
        assertEquals("token123", user.getVerificationToken());
        assertFalse(user.isVerified());

        verify(preparedStatement, times(1)).setString(1, "token123");
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    public void testValidateUser() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("John Doe");
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getString("email")).thenReturn("john.doe@example.com");
        when(resultSet.getInt("type")).thenReturn(1);
        when(resultSet.getString("address")).thenReturn("123 Main St");

        UserDTO user = userDao.validateUser("John Doe", "password");

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("password", user.getPassword());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals(1, user.getType());
        assertEquals("123 Main St", user.getAddress());

        verify(preparedStatement, times(1)).setString(1, "John Doe");
        verify(preparedStatement, times(1)).setString(2, "password");
        verify(preparedStatement, times(1)).executeQuery();
    }

    @AfterEach
    public void tearDown() {
        mockedDBConnection.close();
    }
}
