/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.FWRP.dao;

import static com.FWRP.dao.DAOHelper.logAndClearSQLWarnings;
import com.FWRP.dto.LocationDTO;
import com.FWRP.dto.RetailerDTO;
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
public class RetailerDAO {
    private ServletContext context;

    public RetailerDAO(ServletContext context) {
        this.context = context;
    }
    
    public RetailerDTO retrieve(int userId) {
        RetailerDTO retailer = new RetailerDTO();
        retailer.setUserId(userId);
        String sql = "SELECT name, locationId FROM Retailer WHERE userId = ?";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, retailer.getUserId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                retailer.setName(resultSet.getString("name"));
                retailer.setLocationId(resultSet.getInt("locationId"));
                return retailer;
            }
            // Log and clear any warnings
            logAndClearSQLWarnings("RetailerDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return createDefault(retailer.getUserId());
    }
    
    public RetailerDTO createDefault(int userId) {
        RetailerDTO retailer = new RetailerDTO();
        retailer.setUserId(userId);
        String sql = "SELECT * FROM Retailer WHERE userId = ?";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setInt(1, retailer.getUserId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                retailer.setName(resultSet.getString("name"));
                retailer.setLocationId(resultSet.getInt("locationId"));
                return retailer;
            }
            
            LocationDAO locationDAO = new LocationDAO(context);
            LocationDTO location = locationDAO.getOrCreate("Location not set");
            retailer.setLocationId(location.getId());
            
            String sql2 = "INSERT INTO Retailer (userId,name,locationId) "
                    + "VALUES (?,?,?)";
            try (Connection con2 = DBConnection.getConnection(context);
                 PreparedStatement statement2 = con2.prepareStatement(sql2,RETURN_GENERATED_KEYS)) {

                retailer.setName("Name not set");
                statement2.setInt(1, retailer.getUserId());
                statement2.setString(2, retailer.getName());
                statement2.setInt(3, retailer.getLocationId());
                
                boolean succeeded = statement2.executeUpdate() > 0;

                if (succeeded) {
                    ResultSet generatedKeys = statement2.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        retailer.setUserId(generatedKeys.getInt(1));
                    }
                }
            }
            // Log and clear any warnings
            logAndClearSQLWarnings("RetailerDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retailer;
    }

    public RetailerDTO update(RetailerDTO retailer) {
        String sql = "UPDATE Retailer SET name = ?, locationId = ? WHERE userId = ?";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, retailer.getName());
            statement.setInt(2, retailer.getLocationId());
            statement.setInt(3, retailer.getUserId());
            statement.executeUpdate();
            // Log and clear any warnings
            logAndClearSQLWarnings("RetailerDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retailer;
    }
}
