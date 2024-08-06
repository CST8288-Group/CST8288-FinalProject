/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.FWRP.dao;

import static com.FWRP.dao.DAOHelper.logAndClearSQLWarnings;
import com.FWRP.dto.TransactionDTO;
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
public class TransactionDAO {
    private ServletContext context;

    public TransactionDAO(ServletContext context) {
        this.context = context;
    }
    
    public void addTransaction(TransactionDTO transaction) {
        String sql = "INSERT INTO Transaction (type, quantity, lastUpdate, price, status, datePlaced, userId, transactionentoryId)"
                + " values (?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql,RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, transaction.getType());
            pstmt.setInt(2, transaction.getQuantity());
            pstmt.setLong(3, transaction.getLastUpdate());
            pstmt.setBigDecimal(4, transaction.getPrice());
            pstmt.setInt(5, transaction.getStatus());
            pstmt.setLong(6, transaction.getDatePlaced());
            pstmt.setInt(7, transaction.getUserid());
            pstmt.setInt(8, transaction.getInventoryid());
            
            try {
                boolean succeeded = pstmt.executeUpdate() > 0;
                if (succeeded) {
                    ResultSet generatedKeys = pstmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        transaction.setId(generatedKeys.getInt(1));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
           }
            // Log and clear any warnings
            logAndClearSQLWarnings("TransactionDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
       }
    }
    
    public void updateInventory(TransactionDTO transaction) {
        String sql = "UPDATE Transaction SET type = ?, "
                + "quantity = ?, lastupdate = ?, price = ?"
                + "status = ?, dateplaced = ? userid = ?, inventoryid = ?"
                + "WHERE id = ?";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
             pstmt.setInt(1, transaction.getType());
            pstmt.setInt(2, transaction.getQuantity());
            pstmt.setLong(3, transaction.getLastUpdate());
            pstmt.setBigDecimal(4, transaction.getPrice());
            pstmt.setInt(5, transaction.getStatus());
            pstmt.setLong(6, transaction.getDatePlaced());
            pstmt.setInt(7, transaction.getUserid());
            pstmt.setInt(8, transaction.getInventoryid());
            pstmt.setInt(9, transaction.getId());
            pstmt.executeUpdate();
            // Log and clear any warnings
            logAndClearSQLWarnings("TransactionDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
       }
    }
    public void deleteInventory(TransactionDTO transaction) {
         String sql = "DELETE FROM Transaction WHERE id = ? ";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, transaction.getId());
            pstmt.executeUpdate();
            // Log and clear any warnings
            logAndClearSQLWarnings("Transaction",con);
        } catch (SQLException e) {
            e.printStackTrace();
       }
    }
}
