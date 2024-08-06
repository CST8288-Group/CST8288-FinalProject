package com.FWRP.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.FWRP.dto.UserDTO;
import com.FWRP.utils.DBConnection;
import jakarta.servlet.ServletContext;

public class UserDao {
    private ServletContext context;

    public UserDao(ServletContext context) {
        this.context = context;
    }

    public void registerUser(UserDTO user) throws SQLException {
        String sql = "INSERT INTO users (name, password, email, type, address, verification_token, verified) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection(context);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getType());
            statement.setString(5, user.getAddress());
            statement.setString(6, user.getVerificationToken());
            statement.setBoolean(7, false);
            statement.executeUpdate();
        }
    }

    public void verifyUser(UserDTO user) throws SQLException {
        String query = "UPDATE users SET verified = TRUE, verification_token = NULL WHERE id = ?";
        try (Connection connection = DBConnection.getConnection(context);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, user.getId());
            statement.executeUpdate();
        }
    }

    public UserDTO findByVerificationToken(String token) throws SQLException {
        String sql = "SELECT * FROM users WHERE verification_token = ?";
        try (Connection connection = DBConnection.getConnection(context);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UserDTO user = new UserDTO();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setType(resultSet.getInt("type"));
                user.setAddress(resultSet.getString("address"));
                user.setVerificationToken(resultSet.getString("verification_token"));
                user.setVerified(resultSet.getBoolean("verified"));
                return user;
            }
        }
        return null;
    }

    public UserDTO validateUser(String name, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE name = ? AND password = ? AND verified = TRUE";
        try (Connection connection = DBConnection.getConnection(context);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UserDTO user = new UserDTO();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setType(resultSet.getInt("type"));
                user.setAddress(resultSet.getString("address"));
                return user;
            }
        }
        return null;
    }
}
