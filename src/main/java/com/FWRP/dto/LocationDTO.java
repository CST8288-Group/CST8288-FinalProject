/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.FWRP.dto;

/**
 *
 * @author walter
 */
public class LocationDTO {

    private static final String SELECT_ALL_LOCATION_FIELDS_TEMPLATE = "%1$s.id, %1$s.name";

    private int id;
    private String name;

    public LocationDTO(){}

    public LocationDTO(int id, String name) {
        this.id = id;
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

    public static String getTemplatedSelectStatement(String transactionVarName) {
        return String.format(SELECT_ALL_LOCATION_FIELDS_TEMPLATE, transactionVarName);
    }
}
