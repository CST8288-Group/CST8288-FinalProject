
package com.FWRP.dao;

import com.FWRP.controller.AlertStatus;
import static com.FWRP.dao.DAOHelper.logAndClearSQLWarnings;
import com.FWRP.dto.UserDTO;
import com.FWRP.dto.InventoryDTO;
import com.FWRP.dto.FoodItemDTO;
import com.FWRP.controller.InventoryStatus;
import com.FWRP.controller.UserType;
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

    public InventoryDTO retrieve(int inventoryId) {
                String sql =
                  "SELECT I.* "
                + "FROM Inventory I "
                + "WHERE id = ?;";
        try (Connection con = DBConnection.getConnection(context);
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, inventoryId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Create DTO and populate it
                    InventoryDTO inv = new InventoryDTO();
                    inv.setId(inventoryId);
                    inv.setFoodItemId(rs.getInt("foodItemId"));
                    inv.setQuantity(rs.getInt("quantity"));
                    inv.setExpiration(rs.getDate("expiration"));
                    inv.setStatus(rs.getInt("status"));
                    inv.setDiscountedPrice(rs.getBigDecimal("discountedPrice"));
                    inv.setRetailerId(rs.getInt("retailerId"));
                    return inv;
                }
                // Log and clear any warnings
                logAndClearSQLWarnings("InventoryDAO",con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
                    inv.setFoodItemId(rs.getInt("foodItemId"));
                    ret.setUserId(rs.getInt("retailerId"));
                    inv.setRetailerId(rs.getInt("retailerId"));
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
                    inv.setFoodItemId(rs.getInt("foodItemId"));
                    ret.setUserId(rs.getInt("retailerId"));
                    inv.setRetailerId(rs.getInt("retailerId"));
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
            pstmt.setInt(5, inv.getFoodItemId());
            pstmt.setInt(6, inv.getRetailerId());
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
            pstmt.setInt(5, inv.getFoodItemId());
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
                    inv.setFoodItemId(rs.getInt("foodItemId"));
                    ret.setUserId(rs.getInt("retailerId"));
                    inv.setRetailerId(rs.getInt("retailerId"));
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
    
    private void doNotifications(InventoryDTO inv) {
        String sql = "SELECT S.userId "
                + "FROM PreferredFood PF "
                + "JOIN users U ON U.id = PF.userId "
                + "JOIN Subscription S ON S.userId = PF.userId "
                + "JOIN Retailer R ON S.locationId = R.locationId "
                + "WHERE PF.foodItemId = ? AND U.type = ? AND R.userId = ?;";
        UserType type;
        switch (InventoryStatus.from(inv.getStatus())) {
            case Discounted:
                // for consumers
                type = UserType.Consumer;
                break;
            case Donation:
                // for charities
                type = UserType.Charity;
                break;
            default:
                return;
        }
        try (Connection con = DBConnection.getConnection(context);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, inv.getFoodItemId());
            pstmt.setInt(2, UserType.to(type));
            pstmt.setInt(3, inv.getRetailerId());
            NotificationDAO alertDao = new NotificationDAO(context);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Create DTO and populate it
                    NotificationDTO alert = new NotificationDTO();
                    
                    alert.setInventoryId(inv.getId());
                    alert.setUserId(rs.getInt(1));
                    alert.setType(inv.getStatus());
                    alert.setStatus(AlertStatus.to(AlertStatus.Unread));
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
