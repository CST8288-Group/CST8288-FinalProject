/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.FWRP.dao;

import static com.FWRP.dao.DAOHelper.logAndClearSQLWarnings;

import com.FWRP.dto.*;
import com.FWRP.utils.DBConnection;
import jakarta.servlet.ServletContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 *
 * @author Tiantian, walter
 */
public class SubscriptionDAO {
    private ServletContext context;

    public SubscriptionDAO(ServletContext context) {
        this.context = context;
    }

    public ArrayList<SubscriptionDTO> getSubscriptionsForUser(UserDTO user) {
        String sql = "SELECT S.id, S.userId, L.id, L.name \n" +
                "FROM Subscription S \n" +
                "JOIN Location L ON S.locationId = L.id \n" +
                "WHERE userId = ? \n" +
                "ORDER BY L.name ASC;";
        ArrayList<SubscriptionDTO> result = new ArrayList<>();
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, user.getId());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LocationDTO loc = new LocationDTO(rs.getInt("L.id"),
                            rs.getString("L.name"));
                    SubscriptionDTO sub = new SubscriptionDTO(rs.getInt("S.id"),
                            rs.getInt("S.userId"), loc);
                    result.add(sub);
                }
                logAndClearSQLWarnings("SubscriptionDAO", con);
                return result;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            logAndClearSQLWarnings("SubscriptionDAO", con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addSubscription(SubscriptionDTO subscription) {
        String sql = "SELECT id FROM Subscription WHERE userId = ?"
                + " AND locationId = ?";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, subscription.getUserid());
            statement.setInt(2, subscription.getLocation().getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return;
            }
            
            String sql2 = "INSERT INTO Subscription (userId, locationId)"
                + " values (?,?)";
            try (PreparedStatement pstmt2 = con.prepareStatement(sql2,RETURN_GENERATED_KEYS)) {
                pstmt2.setInt(1, subscription.getUserid());
                pstmt2.setInt(2, subscription.getLocation().getId());
                try {
                    boolean succeeded = pstmt2.executeUpdate() > 0;
                    if (succeeded) {
                        ResultSet generatedKeys = pstmt2.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            subscription.setId(generatedKeys.getInt(1));
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
               }
            }
            // Log and clear any warnings
            logAndClearSQLWarnings("SubscriptionDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }
    
    public void updateSubscription(SubscriptionDTO subscription) {
        String sql = "UPDATE Subscription SET userId = ?,"
                + " locationId = ? WHERE id = ? ";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, subscription.getUserid());
            pstmt.setInt(2, subscription.getLocation().getId());
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
            pstmt.setInt(3, subscription.getLocation().getId());
            pstmt.executeUpdate();
            // Log and clear any warnings
            logAndClearSQLWarnings("SubscriptionDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
       }
    }
    
    
}
