/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.FWRP.dto;

/**
 *
 * @author walter
 */
public class FoodItemDTO {
    private static final String SELECT_ALL_FOOD_ITEM_FIELDS_TEMPLATE = "%1$s.id, %1$s.Name";
    private String name;
    private int id;

    /**
     * Empty Object initialization
     */
    public FoodItemDTO() {}

    /**
     * Query or Creation DTO initialization, for when ID is unknown, especially from user input
     * @param name unique human-readable name of the food article
     */
    public FoodItemDTO(String name) {
        this.name = name;
    }

    /**
     * Result from database as a complete obj, with unique row id
     * @param id row primary key
     * @param name unique human-readable name of the food article
     */
    public FoodItemDTO(int id, String name) {
        this.name = name;
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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

    public static String getTemplatedSelectStatement(String transactionVarName) {
        return String.format(SELECT_ALL_FOOD_ITEM_FIELDS_TEMPLATE, transactionVarName);
    }
}
