/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.FWRP.dto;
import java.math.BigDecimal;
/**
 *
 * @author Tiantian
 */
public class TransactionDTO {
    private int id;
    private int type;
    private int quantity;
    private Long lastUpdate;
    private BigDecimal price;
    private int status;
    private Long datePlaced;
    private int userid;
    private int inventoryid;

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
    public Long getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the lastUpdate to set
     */
    public void setLastUpdate(Long lastUpdate) {
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
    public Long getDatePlaced() {
        return datePlaced;
    }

    /**
     * @param datePlaced the datePlaced to set
     */
    public void setDatePlaced(Long datePlaced) {
        this.datePlaced = datePlaced;
    }

    /**
     * @return the userid
     */
    public int getUserid() {
        return userid;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserid(int userid) {
        this.userid = userid;
    }

    /**
     * @return the inventoryid
     */
    public int getInventoryid() {
        return inventoryid;
    }

    /**
     * @param inventoryid the inventoryid to set
     */
    public void setInventoryid(int inventoryid) {
        this.inventoryid = inventoryid;
    }
    
    
    
    
}
