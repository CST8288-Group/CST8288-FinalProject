/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.FWRP.dao;

import static com.FWRP.dao.DAOHelper.logAndClearSQLWarnings;
import com.FWRP.dto.SubscriptionDTO;
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
public class SubscriptionDAO {
    private ServletContext context;

    public SubscriptionDAO(ServletContext context) {
        this.context = context;
    }
    
    public void addSubscription(SubscriptionDTO subscription) {
        String sql = "INSERT INTO Subscription (userId, locationId)"
                + " values (?,?)";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql,RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, subscription.getUserid());
            pstmt.setInt(2, subscription.getLocationid());
            try {
                boolean succeeded = pstmt.executeUpdate() > 0;
                if (succeeded) {
                    ResultSet generatedKeys = pstmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        subscription.setId(generatedKeys.getInt(1));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
           }
            // Log and clear any warnings
            logAndClearSQLWarnings("SubscriptionDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
       }
    }
    
    public void updateSubscription(SubscriptionDTO subscription) {
        String sql = "UPDATE Subscription SET userId = ?,"
                + " locationId = ? WHERE id = ? ";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, subscription.getUserid());
            pstmt.setInt(2, subscription.getLocationid());
            pstmt.setInt(3, subscription.getId());
            pstmt.executeUpdate();
            // Log and clear any warnings
            logAndClearSQLWarnings("SubscriptionDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
       }
    }
    
    public void deleteSubscription(SubscriptionDTO subscription) {
         String sql = "DELETE FROM Subscription WHERE id = ? AND userId = ? AND locationId = ? ";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, subscription.getId());
            pstmt.setInt(2, subscription.getUserid());
            pstmt.setInt(3, subscription.getLocationid());
            pstmt.executeUpdate();
            // Log and clear any warnings
            logAndClearSQLWarnings("SubscriptionDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
       }
    }
    
    
}
