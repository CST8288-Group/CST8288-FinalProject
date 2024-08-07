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
public enum InventoryStatus {
    Regular(1),
    Donation(2),
    Discounted(3);
    private final int val;
    private InventoryStatus(int v) { val = v; }
    
    // Mapping from integer to UserType
    private static final Map<Integer, InventoryStatus> _map = new HashMap<Integer, InventoryStatus>();
    static
    {
        for (InventoryStatus status : InventoryStatus.values())
            _map.put(status.val, status);
    }
 
    /**
     * Get InventoryType from value
     * @param value Value
     * @return invType
     */
    public static InventoryStatus from(int value)
    {
        return _map.get(value);
    }
    public static int to(InventoryStatus is)
    {
        return is.val;
    }
}
