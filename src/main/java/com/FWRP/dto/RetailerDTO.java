/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.FWRP.dto;

/**
 *
 * @author walter
 */
public class RetailerDTO {

    private static final String SELECT_ALL_RETAILER_FIELDS_TEMPLATE = "%1$s.userId, %1$s.name, %1$s.locationId";

    private String name;
    private LocationDTO location;
    private int userId;

    public RetailerDTO(){}

    public RetailerDTO(String name, LocationDTO location, int userId) {
        this.name = name;
        this.location = location;
        this.userId = userId;
    }

    /**
     * @return the name
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
     * @return the location
     */
    public LocationDTO getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(LocationDTO location) {
        this.location = location;
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
        return String.format(SELECT_ALL_RETAILER_FIELDS_TEMPLATE, transactionVarName);
    }
}
