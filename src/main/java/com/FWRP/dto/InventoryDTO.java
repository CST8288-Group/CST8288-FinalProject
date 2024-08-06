/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.FWRP.dto;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author walter
 */
public class InventoryDTO {
    private int id;
    private int quantity;
    private Date expiration;
    private int status;
    private BigDecimal discountedPrice;
    private FoodItemDTO foodItem;
    private int retailerId;

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
     * @return the expiration
     */
    public Date getExpiration() {
        return expiration;
    }

    /**
     * @param expiration the expiration to set
     */
    public void setExpiration(Date expiration) {
        this.expiration = expiration;
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
     * @return the discountedPrice
     */
    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    /**
     * @param discountedPrice the discountedPrice to set
     */
    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }
    
    /**
     * @return the retailerId
     */
    public int getRetailerId() {
        return retailerId;
    }

    /**
     * @param retailerId the retailerId to set
     */
    public void setRetailerId(int retailerId) {
        this.retailerId = retailerId;
    }

    /**
     * @return the foodItem
     */
    public FoodItemDTO getFoodItem() {
        return foodItem;
    }

    /**
     * @param foodItem the foodItem to set
     */
    public void setFoodItem(FoodItemDTO foodItem) {
        this.foodItem = foodItem;
    }

}
