/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.FWRP.dao;

import static com.FWRP.dao.DAOHelper.logAndClearSQLWarnings;
import com.FWRP.dto.PreferredFoodDTO;
import com.FWRP.dto.UserDTO;
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
 * @author Tiantian, Walter
 */
public class PreferredFoodDAO {
     private ServletContext context;

    public PreferredFoodDAO(ServletContext context) {
        this.context = context;
    }
    
    
    public ArrayList<PreferredFoodDTO> getPreferredFoods(UserDTO user) {
        ArrayList<PreferredFoodDTO> result = new ArrayList<>();
        String sql =
                  "SELECT PF.*"
                + "FROM PreferredFood PF "
                + "JOIN FoodItem FI ON FI.id = PF.foodItemId "
                + "WHERE userId = ? GROUP BY FI.id ORDER BY FI.name ASC ";
        try (Connection con = DBConnection.getConnection(context);
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, user.getId());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PreferredFoodDTO prefDTO = new PreferredFoodDTO(
                            rs.getInt("id"),
                            rs.getInt("userId"),
                            rs.getInt("foodItemId"));
                    result.add(prefDTO);
                }
                // Log and clear any warnings
                logAndClearSQLWarnings("PreferredFoodDAO",con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public void addPreferredFood(PreferredFoodDTO pfood) {
        String sql = "SELECT id FROM PreferredFood WHERE userId = ?"
                + " AND foodItemId = ?";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, pfood.getUserId());
            statement.setInt(2, pfood.getFoodItemId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return;
            }
            
            String sql2 = "INSERT INTO PreferredFood (userId, foodItemId)"
                + " values (?,?)";
            try (PreparedStatement pstmt2 = con.prepareStatement(sql2,RETURN_GENERATED_KEYS)) {
                pstmt2.setInt(1, pfood.getUserId());
                pstmt2.setInt(2, pfood.getFoodItemId());
                try {
                    boolean succeeded = pstmt2.executeUpdate() > 0;
                    if (succeeded) {
                        ResultSet generatedKeys = pstmt2.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            pfood.setId(generatedKeys.getInt(1));
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
               }
            }
            // Log and clear any warnings
            logAndClearSQLWarnings("PreferredFoodDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    public void updatePreferredFood(PreferredFoodDTO pfood) {
        String sql = "UPDATE PreferredFood SET userId = ?,"
                + " foodItemId = ? WHERE id = ? ";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, pfood.getUserId());
            pstmt.setInt(2, pfood.getFoodItemId());
            pstmt.setInt(3, pfood.getId());
            pstmt.executeUpdate();
            // Log and clear any warnings
            logAndClearSQLWarnings("PreferredFoodDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
       }
    }
    
    public void deletePreferredFood(PreferredFoodDTO pfood) {
         String sql = "DELETE FROM PreferredFood WHERE id = ? AND userId = ? ";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, pfood.getId());
            pstmt.setInt(2, pfood.getUserId());
            pstmt.executeUpdate();
            // Log and clear any warnings
            logAndClearSQLWarnings("PreferredFoodDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
       }
    }
    
}
