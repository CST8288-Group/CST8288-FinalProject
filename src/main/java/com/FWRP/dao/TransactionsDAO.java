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
                  " SELECT T.*, FI.*, I.*, L.*, R.* \n" +
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
                    TransactionDTO tx = new TransactionDTO();
                    InventoryDTO inv = new InventoryDTO();
                    FoodItemDTO fi = new FoodItemDTO();
                    LocationDTO loc = new LocationDTO();
                    RetailerDTO retailer = new RetailerDTO();

                    tx.setId(rs.getInt(1));
                    tx.setType(rs.getInt(2));
                    tx.setQuantity(rs.getInt(3));
                    tx.setLastUpdate(rs.getTimestamp(4));
                    tx.setPrice(rs.getBigDecimal(5));
                    tx.setStatus(rs.getInt(6));
                    tx.setDatePlaced(rs.getTimestamp(7));
                    tx.setUserId(rs.getInt(8));
                    
                    fi.setId(rs.getInt(10));
                    fi.setName(rs.getString(11));
                    
                    inv.setId(rs.getInt(12));
                    inv.setQuantity(rs.getInt(13));
                    inv.setExpiration(rs.getDate(14));
                    inv.setStatus(rs.getInt(15));
                    inv.setDiscountedPrice(rs.getBigDecimal(16));
                    inv.setFoodItem(fi);
                    tx.setInventory(inv);
                    
                    retailer.setUserId(rs.getInt(18));
                    inv.setRetailer(retailer);
                    
                    loc.setId(rs.getInt(19));
                    loc.setName(rs.getString(20));                    
                    
                    retailer.setUserId(rs.getInt(21));
                    retailer.setLocation(loc);
                    retailer.setName(rs.getString(23));

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
