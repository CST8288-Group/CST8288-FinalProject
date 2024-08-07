
package com.FWRP.dao;

import static com.FWRP.dao.DAOHelper.logAndClearSQLWarnings;
import com.FWRP.dto.UserDTO;
import com.FWRP.dto.InventoryDTO;
import com.FWRP.dto.FoodItemDTO;
import com.FWRP.controller.InventoryStatus;
import com.FWRP.dto.NotificationDTO;
import com.FWRP.dto.RetailerDTO;
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
 * @author walter
 */
public class InventoryDAO {
    private ServletContext context;

    public InventoryDAO(ServletContext context) {
        this.context = context;
    }

    public ArrayList<InventoryDTO> getInventoryForUser(UserDTO user) {
        ArrayList<InventoryDTO> result = new ArrayList<>();
        String sql =
                  "SELECT FI.name, I.* "
                + "FROM Inventory I "
                + "JOIN FoodItem FI ON I.foodItemId = FI.id "
                + "WHERE retailerId = ? ORDER BY expiration ASC ";
        try (Connection con = DBConnection.getConnection(context);
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, user.getId());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Create DTO and populate it
                    InventoryDTO inv = new InventoryDTO();
                    FoodItemDTO fi = new FoodItemDTO();
                    RetailerDTO ret = new RetailerDTO();
                    inv.setId(rs.getInt("id"));
                    inv.setQuantity(rs.getInt("quantity"));
                    inv.setExpiration(rs.getDate("expiration"));
                    inv.setStatus(rs.getInt("status"));
                    inv.setDiscountedPrice(rs.getBigDecimal("discountedPrice"));
                    fi.setId(rs.getInt("foodItemId"));
                    fi.setName(rs.getString("name"));
                    inv.setFoodItem(fi);
                    ret.setUserId(rs.getInt("retailerId"));
                    inv.setRetailer(ret);
                    // Add it to results list
                    result.add(inv);
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
    public ArrayList<InventoryDTO> getExpiringWithin7(UserDTO user) {
        ArrayList<InventoryDTO> result = new ArrayList<>();
        String sql =
                  "SELECT FI.name, I.* "
                + "FROM Inventory I "
                + "JOIN FoodItem FI ON I.foodItemId = FI.id "
                + "WHERE retailerId = ? AND I.expiration BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY)";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, user.getId());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Create DTO and populate it
                    InventoryDTO inv = new InventoryDTO();
                    FoodItemDTO fi = new FoodItemDTO();
                    RetailerDTO ret = new RetailerDTO();
                    inv.setId(rs.getInt("id"));
                    inv.setQuantity(rs.getInt("quantity"));
                    inv.setExpiration(rs.getDate("expiration"));
                    inv.setStatus(rs.getInt("status"));
                    inv.setDiscountedPrice(rs.getBigDecimal("discountedPrice"));
                    fi.setId(rs.getInt("foodItemId"));
                    fi.setName(rs.getString("name"));
                    inv.setFoodItem(fi);
                    ret.setUserId(rs.getInt("retailerId"));
                    inv.setRetailer(ret);
                    // Add it to results list
                    result.add(inv);
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
    
    public void addInventoryFood(InventoryDTO inv) {
        String sql = "INSERT INTO Inventory (quantity,expiration,status,discountedPrice,foodItemId,retailerId)"
                + " values (?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql,RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, inv.getQuantity());
            pstmt.setDate(2, inv.getExpiration());
            pstmt.setInt(3, inv.getStatus());
            pstmt.setBigDecimal(4, inv.getDiscountedPrice());
            pstmt.setInt(5, inv.getFoodItem().getId());
            pstmt.setInt(6, inv.getRetailer().getUserId());
            try {
                boolean succeeded = pstmt.executeUpdate() > 0;
                if (succeeded) {
                    ResultSet generatedKeys = pstmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        inv.setId(generatedKeys.getInt(1));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
           }
            // Log and clear any warnings
            logAndClearSQLWarnings("InventoryDAO",con);
            
            doNotifications(inv);
        } catch (SQLException e) {
            e.printStackTrace();
       }
    }

    public void updateInventory(InventoryDTO inv) {
        String sql = "UPDATE Inventory "
                + "SET quantity = ?,  expiration = ?, status = ?,"
                + " discountedPrice = ?, foodItemId = ? "
                + "WHERE id = ? ";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, inv.getQuantity());
            pstmt.setDate(2, inv.getExpiration());
            pstmt.setInt(3, inv.getStatus());
            pstmt.setBigDecimal(4, inv.getDiscountedPrice());
            pstmt.setInt(5, inv.getFoodItem().getId());
            pstmt.setInt(6, inv.getId());
            pstmt.executeUpdate();
            // Log and clear any warnings
            logAndClearSQLWarnings("InventoryDAO",con);
            
            doNotifications(inv);
        } catch (SQLException e) {
            e.printStackTrace();
       }
    }
    
    public void deleteInventory(InventoryDTO inv) {
         String sql = "DELETE FROM Inventory "
                 + "WHERE id = ? ";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, inv.getId());
            pstmt.executeUpdate();
            // Log and clear any warnings
            logAndClearSQLWarnings("InventoryDAO",con);
        } catch (SQLException e) {
            e.printStackTrace();
       }
    }
    
    public ArrayList<InventoryDTO> getAvailableFood(InventoryStatus is) {
        String sql = "SELECT * "
                + "FROM Inventory I "
                + "JOIN FoodItem FI ON I.foodItemId = FI.id "
                + "WHERE status = ? AND quantity > 0 "
                + "ORDER BY expiration ASC";
        ArrayList<InventoryDTO> result = new ArrayList<>();
        try (Connection con = DBConnection.getConnection(context);
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, InventoryStatus.to(is));
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Create DTO and populate it
                    InventoryDTO inv = new InventoryDTO();
                    FoodItemDTO fi = new FoodItemDTO();
                    RetailerDTO ret = new RetailerDTO();
                    inv.setId(rs.getInt("id"));
                    inv.setQuantity(rs.getInt("quantity"));
                    inv.setExpiration(rs.getDate("expiration"));
                    inv.setStatus(rs.getInt("status"));
                    inv.setDiscountedPrice(rs.getBigDecimal("discountedPrice"));
                    fi.setId(rs.getInt("foodItemId"));
                    fi.setName(rs.getString("name"));
                    inv.setFoodItem(fi);
                    ret.setUserId(rs.getInt("retailerId"));
                    inv.setRetailer(ret);
                    // Add it to results list
                    result.add(inv);
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
    
    void doNotifications(InventoryDTO inv) {
        String sql = "SELECT S.userId "
                + "FROM Subscription S, Retailer R, PreferedFood PF "
                + "WHERE R.userId = ? AND R.locationId = S.locationId "
                + "AND PF.foodItemId = ?;";
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setInt(1, inv.getRetailer().getUserId());
            pstmt.setInt(2, inv.getFoodItem().getId());
            NotificationDAO alertDao = new NotificationDAO(context);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Create DTO and populate it
                    NotificationDTO alert = new NotificationDTO();
                    
                    alert.setInventory(inv);
                    alert.setUserId(rs.getInt(1));
                    alertDao.newNotification(alert);
                }
                // Log and clear any warnings
                logAndClearSQLWarnings("InventoryDAO",con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
