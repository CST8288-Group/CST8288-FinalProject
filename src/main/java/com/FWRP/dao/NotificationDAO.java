/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.FWRP.dao;

import com.FWRP.controller.NotificationStatus;
import static com.FWRP.dao.DAOHelper.logAndClearSQLWarnings;
import com.FWRP.dto.FoodItemDTO;
import com.FWRP.dto.InventoryDTO;
import com.FWRP.dto.LocationDTO;
import com.FWRP.dto.NotificationDTO;
import com.FWRP.dto.RetailerDTO;
import com.FWRP.dto.UserDTO;
import com.FWRP.utils.DBConnection;
import jakarta.servlet.ServletContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import java.util.ArrayList;

/**
 *
 * @author Tiantian
 */
public class NotificationDAO {
    private ServletContext context;

    public NotificationDAO(ServletContext context) {
        this.context = context;
    }
    
    public void newNotification(NotificationDTO notification) {
        String sql = "INSERT INTO Notification (timestamp, type, status, userid, Inventoryid)"
                + " values (CURRENT_TIMESTAMP(),?,?,?,?)";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, notification.getInventory().getStatus());
            pstmt.setInt(2, NotificationStatus.to(NotificationStatus.Unread));
            pstmt.setInt(3, notification.getUserId());
            pstmt.setInt(4, notification.getInventory().getId());
            try {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
           }
            // Log and clear any warnings
            logAndClearSQLWarnings("NotificationDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
       }
    }
    
        public ArrayList<NotificationDTO> getNotificationsForUser(UserDTO user) {
        ArrayList<NotificationDTO> result = new ArrayList<>();
        String sql =
                  "SELECT N.*, I.*, FI.name, L.*, R.name "
                + "FROM Notification N "
                + "JOIN Inventory I ON N.inventoryId = I.id "
                + "JOIN FoodItem FI ON I.foodItemId = FI.id "
                + "JOIN Retailer R ON I.retailerId = R.userId "
                + "JOIN Location L ON R.locationId = L.id "
                + "WHERE N.userId = ? ORDER BY N.timestamp DESC ";
        try (Connection con = DBConnection.getConnection(context);
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, user.getId());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Create DTO and populate it
                    NotificationDTO alert = new NotificationDTO();
                    InventoryDTO inv = new InventoryDTO();
                    LocationDTO loc = new LocationDTO();
                    RetailerDTO retailer = new RetailerDTO();
                    FoodItemDTO fi = new FoodItemDTO();

                    alert.setId(rs.getInt(1));
                    alert.setTimestamp(rs.getTimestamp(2));
                    alert.setType(rs.getInt(3));
                    alert.setStatus(rs.getInt(4));
                    alert.setUserId(rs.getInt(5));
                    inv.setId(rs.getInt(6));
                    alert.setInventory(inv);
                    
                    inv.setRetailer(retailer);
                    inv.setQuantity(rs.getInt(8));
                    inv.setExpiration(rs.getDate(9));
                    inv.setStatus(rs.getInt(10));
                    inv.setDiscountedPrice(rs.getBigDecimal(11));
                    inv.setFoodItem(fi);

                    fi.setId(rs.getInt(12));
                    retailer.setUserId(rs.getInt(13));
                    fi.setName(rs.getString(14));
                    
                    loc.setId(rs.getInt(15));
                    loc.setName(rs.getString(16));                    
                    
                    retailer.setLocation(loc);
                    retailer.setName(rs.getString(17));

                    // Add it to results list
                    result.add(alert);
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
    
    public void addNotification(NotificationDTO notification) {
        String sql = "INSERT INTO Notification (timestamp, type, status, userid, Inventoryid)"
                + " values (?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql,RETURN_GENERATED_KEYS)) {
            pstmt.setTimestamp(1, notification.getTimestamp());
            pstmt.setInt(2, notification.getType());
            pstmt.setInt(3, notification.getStatus());
            pstmt.setInt(4, notification.getUserId());
            pstmt.setInt(5, notification.getInventory().getId());
            try {
                boolean succeeded = pstmt.executeUpdate() > 0;
                if (succeeded) {
                    ResultSet generatedKeys = pstmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        notification.setId(generatedKeys.getInt(1));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
           }
            // Log and clear any warnings
            logAndClearSQLWarnings("NotificationDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
       }
    }
    
    public void updateNotification(NotificationDTO notification) {
        String sql = "UPDATE Notification SET timestamp = ?,"
                + " type = ?, status = ?, userId = ?,"
                + " inventoryId = ? WHERE id = ? ";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setTimestamp(1, notification.getTimestamp());
            pstmt.setInt(2, notification.getType());
            pstmt.setInt(3, notification.getStatus());
            pstmt.setInt(4, notification.getUserId());
            pstmt.setInt(5, notification.getInventory().getId());
            pstmt.setInt(6, notification.getId());
            pstmt.executeUpdate();
            // Log and clear any warnings
            logAndClearSQLWarnings("NotificationDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
       }
    }
    
    public void deleteNotification(NotificationDTO notification) {
         String sql = "DELETE FROM Notification WHERE id = ? ";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, notification.getId());
            pstmt.executeUpdate();
            // Log and clear any warnings
            logAndClearSQLWarnings("NotificationDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
       }
    }
    
}
