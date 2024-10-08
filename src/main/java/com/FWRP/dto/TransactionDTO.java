/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.FWRP.dto;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class TransactionDTO {

    private static final String SELECT_ALL_TRANSACTION_FIELDS_TEMPLATE = 
            "%1$s.id, %1$s.type, %1$s.quantity, %1$s.lastUpdate, %1$s.price, %1$s.status,"
            + " %1$s.datePlaced, %1$s.userId, %1$s.inventoryId";
    private int id;
    private int type;
    private int quantity;
    private Timestamp lastUpdate;
    private BigDecimal price;
    private int status;
    private java.sql.Timestamp datePlaced;
    private int userId;
    private InventoryDTO inventory;

    public TransactionDTO(int id, int type, int quantity, Timestamp lastUpdate, BigDecimal price, int status,
                          Timestamp datePlaced, int userId, InventoryDTO inventory) {
        this.id = id;
        this.type = type;
        this.quantity = quantity;
        this.lastUpdate = lastUpdate;
        this.price = price;
        this.status = status;
        this.datePlaced = datePlaced;
        this.userId = userId;
        this.inventory = inventory;
    }
    
    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the lastUpdate
     */
    public java.sql.Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the lastUpdate to set
     */
    public void setLastUpdate(java.sql.Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the datePlaced
     */
    public java.sql.Timestamp getDatePlaced() {
        return datePlaced;
    }

    /**
     * @param datePlaced the datePlaced to set
     */
    public void setDatePlaced(java.sql.Timestamp datePlaced) {
        this.datePlaced = datePlaced;
    }
    
    /**
     * @return the inventory
     */
    public InventoryDTO getInventory() {
        return inventory;
    }

    /**
     * @param inventory the inventory to set
     */
    public void setInventory(InventoryDTO inventory) {
        this.inventory = inventory;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static String getTemplatedSelectStatement(String transactionVarName) {
        return String.format(SELECT_ALL_TRANSACTION_FIELDS_TEMPLATE, transactionVarName);
    }

}