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
import java.util.ArrayList;

/**
 *
 * @author walter
 */
public class FoodItemDAO {
    private ServletContext context;

    public FoodItemDAO(ServletContext context) {
        this.context = context;
    }
    
    public FoodItemDTO getOrCreate(String name) {
        FoodItemDTO foodItem = new FoodItemDTO();
        foodItem.setName(name);
        String sql = "SELECT id FROM FoodItem WHERE name = ?";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, name);
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
                        return foodItem;
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

    public ArrayList<FoodItemDTO> getAllFoodItems() {
        String sql = "SELECT id, name FROM FoodItem ORDER BY name DESC;";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                ArrayList<FoodItemDTO> result = new ArrayList<>();
                while (rs.next()) {
                    FoodItemDTO foodItem = new FoodItemDTO(rs.getInt("id"),
                            rs.getString("name"));
                    result.add(foodItem);
                }
                logAndClearSQLWarnings("FoodItemDAO", con);
                return result;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // Log and clear any warnings
            logAndClearSQLWarnings("FoodItemDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public FoodItemDTO retrieve(int id) {
        String sql = "SELECT name FROM FoodItem WHERE id = ?";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                FoodItemDTO foodItem = new FoodItemDTO();
                foodItem.setId(id);
                foodItem.setName(resultSet.getString("name"));
                return foodItem;
            }
            // Log and clear any warnings
            logAndClearSQLWarnings("LocationDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
