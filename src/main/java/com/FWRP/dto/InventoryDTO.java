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
    private static final String SELECT_ALL_INVENTORY_FIELDS_TEMPLATE = "%1$s.id, %1$s.quantity, %1$s.expiration, %1$s.status, %1$s.discountedPrice, %1$s.foodItemId, %1$s.retailerId";

    private int id;
    private int quantity;
    private Date expiration;
    private int status;
    private BigDecimal discountedPrice;
    private FoodItemDTO foodItem;
    private RetailerDTO retailer;

    public InventoryDTO(){}

    public InventoryDTO(int id, int quantity, Date expiration, int status, BigDecimal discountedPrice,
                        FoodItemDTO foodItem, RetailerDTO retailer) {
        this.id = id;
        this.quantity = quantity;
        this.expiration = expiration;
        this.status = status;
        this.discountedPrice = discountedPrice;
        this.foodItem = foodItem;
        this.retailer = retailer;
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

    /**
     * @return the retailer
     */
    public RetailerDTO getRetailer() {
        return retailer;
    }

    /**
     * @param retailer the retailer to set
     */
    public void setRetailer(RetailerDTO retailer) {
        this.retailer = retailer;
    }

    public static String getTemplatedSelectStatement(String transactionVarName) {
        return String.format(SELECT_ALL_INVENTORY_FIELDS_TEMPLATE, transactionVarName);
    }

}
