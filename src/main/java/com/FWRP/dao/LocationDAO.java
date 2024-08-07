/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.FWRP.dao;

import static com.FWRP.dao.DAOHelper.logAndClearSQLWarnings;
import com.FWRP.dto.LocationDTO;
import com.FWRP.utils.DBConnection;
import jakarta.servlet.ServletContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 *
 * @author walter
 */
public class LocationDAO {
    private ServletContext context;

    public LocationDAO(ServletContext context) {
        this.context = context;
    }
    
    public LocationDTO getOrCreate(LocationDTO location) {
        String sql = "SELECT id FROM Location WHERE name = ?";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, location.getName());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                location.setId(resultSet.getInt("id"));
                return location;
            }
            
            String sql2 = "INSERT INTO Location (name) VALUES (?)";
            try (PreparedStatement statement2 = con.prepareStatement(sql2,RETURN_GENERATED_KEYS)) {
                statement2.setString(1, location.getName());
                
                boolean succeeded = statement2.executeUpdate() > 0;

                if (succeeded) {
                    ResultSet generatedKeys = statement2.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        location.setId(generatedKeys.getInt(1));
                    }
                }
            }
            // Log and clear any warnings
            logAndClearSQLWarnings("LocationDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public LocationDTO retrieve(LocationDTO location) {
        String sql = "SELECT name FROM Location WHERE id = ?";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, location.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                location.setName(resultSet.getString("name"));
                return location;
            }
            // Log and clear any warnings
            logAndClearSQLWarnings("LocationDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
