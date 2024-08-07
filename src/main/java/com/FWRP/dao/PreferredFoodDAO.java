/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.FWRP.dao;

import static com.FWRP.dao.DAOHelper.logAndClearSQLWarnings;
import com.FWRP.dto.PreferredFoodDTO;
import com.FWRP.utils.DBConnection;
import jakarta.servlet.ServletContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 *
 * @author Tiantian
 */
public class PreferredFoodDAO {
     private ServletContext context;

    public PreferredFoodDAO(ServletContext context) {
        this.context = context;
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
