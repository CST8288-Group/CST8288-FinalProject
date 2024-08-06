/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.FWRP.controller;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author walter
 */
public enum UserType {
    Retailer(1),
    Consumer(2),
    Charity(3);
    private final int val;
    private UserType(int v) { val = v; }
    
    // Mapping from integer to UserType
    private static final Map<Integer, UserType> _map = new HashMap<Integer, UserType>();
    static
    {
        for (UserType userType : UserType.values())
            _map.put(userType.val, userType);
    }
 
    /**
     * Get UserType from value
     * @param value Value
     * @return UserType
     */
    public static UserType from(int value)
    {
        return _map.get(value);
    }
    public static int to(UserType ut)
    {
        return ut.val;
    }
}
