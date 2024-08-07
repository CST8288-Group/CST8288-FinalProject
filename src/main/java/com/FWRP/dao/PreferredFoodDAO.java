/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.FWRP.dao;

import static com.FWRP.dao.DAOHelper.logAndClearSQLWarnings;
import com.FWRP.dto.FoodItemDTO;
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
    
    
    public ArrayList<FoodItemDTO> getPreferredFood(UserDTO user) {
        ArrayList<FoodItemDTO> result = new ArrayList<>();
        String sql =
                  "SELECT FI.*"
                + "FROM PreferedFood PF "
                + "JOIN FoodItem FI ON FI.id = PF.foodItemId "
                + "WHERE userId = ? GROUP BY FI.id ORDER BY FI.name ASC ";
        try (Connection con = DBConnection.getConnection(context);
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, user.getId());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    FoodItemDTO foodItemDTO = new FoodItemDTO();
                    foodItemDTO.setId(rs.getInt("id"));
                    foodItemDTO.setName(rs.getString("name"));
                }
                // Log and clear any warnings
                logAndClearSQLWarnings("InventoryDAO",con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public void addPreferredFood(PreferredFoodDTO pfood) {
        String sql = "INSERT INTO PreferredFood (userid, fooditemid)"
                + " values (?,?)";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql,RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, pfood.getUserid());
            pstmt.setInt(2, pfood.getFooditemid());
            try {
                boolean succeeded = pstmt.executeUpdate() > 0;
                if (succeeded) {
                    ResultSet generatedKeys = pstmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        pfood.setId(generatedKeys.getInt(1));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
           }
            // Log and clear any warnings
            logAndClearSQLWarnings("PreferredFoodDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
       }
    }

    public void updatePreferredFood(PreferredFoodDTO pfood) {
        String sql = "UPDATE PreferredFood SET userId = ?,"
                + " foodItemId = ? WHERE id = ? ";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, pfood.getUserid());
            pstmt.setInt(2, pfood.getFooditemid());
            pstmt.setInt(3, pfood.getId());
            pstmt.executeUpdate();
            // Log and clear any warnings
            logAndClearSQLWarnings("PreferredFoodDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
       }
    }
    
    public void deletePreferredFood(PreferredFoodDTO pfood) {
         String sql = "DELETE FROM PreferredFood WHERE id = ? AND userId = ? AND foodItemId = ? ";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, pfood.getId());
            pstmt.setInt(2, pfood.getUserid());
            pstmt.setInt(3, pfood.getFooditemid());
            pstmt.executeUpdate();
            // Log and clear any warnings
            logAndClearSQLWarnings("PreferredFoodDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
       }
    }
    
}
