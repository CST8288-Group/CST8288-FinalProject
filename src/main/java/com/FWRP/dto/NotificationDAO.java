/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.FWRP.dao;

import static com.FWRP.dao.DAOHelper.logAndClearSQLWarnings;
import com.FWRP.dto.NotificationDTO;
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
public class NotificationDAO {
    private ServletContext context;

    public NotificationDAO(ServletContext context) {
        this.context = context;
    }
    
    public void addNotification(NotificationDTO notification) {
        String sql = "INSERT INTO Notification (timestamp, type, status, userid, Inventoryid)"
                + " values (?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql,RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, notification.getTimestamp());
            pstmt.setInt(2, notification.getType());
            pstmt.setInt(3, notification.getStatus());
            pstmt.setInt(4, notification.getUserid());
            pstmt.setInt(5, notification.getInventoryid());
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
                + " type = ?, status = ?, userid = ?,"
                + " inventoryid = ? WHERE id = ? ";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, notification.getTimestamp());
            pstmt.setInt(2, notification.getType());
            pstmt.setInt(3, notification.getStatus());
            pstmt.setInt(4, notification.getUserid());
            pstmt.setInt(5, notification.getInventoryid());
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
