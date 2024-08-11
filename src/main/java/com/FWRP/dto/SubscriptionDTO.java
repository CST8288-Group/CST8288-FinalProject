/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.FWRP.dto;

/**
 *
 * @author Tiantian
 */
public class SubscriptionDTO {
    private int id;
    private int userid;
    private LocationDTO location;

    public SubscriptionDTO() {}

    public SubscriptionDTO(int id, int userid, LocationDTO location) {
        this.id = id;
        this.userid = userid;
        this.location = location;
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
     * @return the locationid
     */
    public LocationDTO getLocation() {
        return location;
    }

    /**
     * @param location the locationdto to set
     */
    public void setLocation(LocationDTO location) {
        this.location = location;
    }
    
    
}
