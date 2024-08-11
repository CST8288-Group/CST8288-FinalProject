/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.FWRP.dao;

import com.FWRP.controller.InventoryStatus;
import com.FWRP.controller.UserType;
import static com.FWRP.dao.DAOHelper.logAndClearSQLWarnings;
import com.FWRP.dto.FoodItemDTO;
import com.FWRP.dto.InventoryDTO;
import com.FWRP.dto.LocationDTO;
import com.FWRP.dto.RetailerDTO;
import com.FWRP.dto.TransactionDTO;
import com.FWRP.dto.UserDTO;
import com.FWRP.utils.DBConnection;
import jakarta.servlet.ServletContext;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author walter
 */
public class TransactionsDAO {
    private ServletContext context;

    public TransactionsDAO(ServletContext context) {
        this.context = context;
    }
    
    public ArrayList<TransactionDTO> getTransactionsForUser(UserDTO user) {
        ArrayList<TransactionDTO> result = new ArrayList<>();
        String sql =
                  " SELECT " + TransactionDTO.getTemplatedSelectStatement("T") +
                    ", " + FoodItemDTO.getTemplatedSelectStatement("FI") +
                    ", " + InventoryDTO.getTemplatedSelectStatement("I") +
                    ", " + LocationDTO.getTemplatedSelectStatement("L") +
                    ", " + RetailerDTO.getTemplatedSelectStatement("R") + "\n" +
                    "FROM Transaction T \n" +
                    "JOIN Inventory I ON T.inventoryId = I.id \n" +
                    "JOIN FoodItem FI ON I.foodItemId = FI.id \n" +
                    "JOIN Retailer R ON I.retailerId = R.userId \n" +
                    "JOIN Location L ON R.locationId = L.id \n" +
                    "WHERE T.userId = ? ORDER BY expiration ASC ; ";
        try (Connection con = DBConnection.getConnection(context);
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, user.getId());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Create DTO and populate it

                    LocationDTO loc = new LocationDTO(rs.getInt("L.id"), rs.getString("L.name"));

                    InventoryDTO inv = new InventoryDTO(rs.getInt("I.id"),
                            rs.getInt("I.quantity"),
                            rs.getDate("I.expiration"),
                            rs.getInt("I.status"),
                            rs.getBigDecimal("I.discountedPrice"),
                            rs.getInt("FI.id"),
                             rs.getInt("R.userId"));

                    TransactionDTO tx = new TransactionDTO(rs.getInt("T.id"),
                            rs.getInt("T.type"),
                            rs.getInt("T.quantity"),
                            rs.getTimestamp("T.lastUpdate"),
                            rs.getBigDecimal("T.price"),
                            rs.getInt("T.status"),
                            rs.getTimestamp("T.datePlaced"),
                            rs.getInt("T.userId"),
                            inv);

                    // Add it to results list
                    result.add(tx);
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
    
    public void transferItems(UserDTO recv, InventoryDTO inv, int quantity) {
        
        InventoryStatus expected;
        switch (UserType.from(recv.getType())) {
            case Consumer:
                expected = InventoryStatus.Discounted;
                break;
            case Charity:
                expected = InventoryStatus.Donation;
                break;
            default:
                return;
        }
        
        try (Connection con = DBConnection.getConnection(context);) {
            // Prepare the callable statement
            String sql = "{CALL transfer_inventory(?, ?, ?, ?)}";
            try (CallableStatement cstmt = con.prepareCall(sql)) {

                // Set input parameters
                cstmt.setLong(1, recv.getId());
                cstmt.setInt(2, inv.getId());
                cstmt.setInt(3, quantity);
                cstmt.setInt(4, InventoryStatus.to(expected));

                // Execute the stored procedure
                cstmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
