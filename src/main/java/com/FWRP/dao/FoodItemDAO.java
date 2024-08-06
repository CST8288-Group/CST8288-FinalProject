/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.FWRP.dao;

import static com.FWRP.dao.DAOHelper.logAndClearSQLWarnings;
import com.FWRP.dto.FoodItemDTO;
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
public class FoodItemDAO {
    private ServletContext context;

    public FoodItemDAO(ServletContext context) {
        this.context = context;
    }
    
    public FoodItemDTO getOrCreate(FoodItemDTO foodItem) {
        String sql = "SELECT id FROM FoodItem WHERE name = ?";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, foodItem.getName());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                foodItem.setId(resultSet.getInt("id"));
                return foodItem;
            }
            
            String sql2 = "INSERT INTO FoodItem (name) VALUES (?)";
            try (PreparedStatement statement2 = con.prepareStatement(sql2,RETURN_GENERATED_KEYS)) {
                statement2.setString(1, foodItem.getName());
                
                boolean succeeded = statement2.executeUpdate() > 0;

                if (succeeded) {
                    ResultSet generatedKeys = statement2.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        foodItem.setId(generatedKeys.getInt(1));
                    }
                }
            }
            // Log and clear any warnings
            logAndClearSQLWarnings("FoodItemDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
