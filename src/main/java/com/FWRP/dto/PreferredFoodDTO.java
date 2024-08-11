/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.FWRP.dto;

/**
 *
 * @author Tiantian
 */
public class PreferredFoodDTO {
    private int id;
    private int userId;
    private int foodItemId;

    public PreferredFoodDTO(int id, int userId, int foodItemId) {
        this.id = id;
        this.userId = userId;
        this.foodItemId = foodItemId;
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

    /**
     * @return the foodItemId
     */
    public int getFoodItemId() {
        return foodItemId;
    }

    /**
     * @param foodItemId the foodItemId to set
     */
    public void setFoodItemid(int foodItemId) {
        this.foodItemId = foodItemId;
    }
    
    
}
