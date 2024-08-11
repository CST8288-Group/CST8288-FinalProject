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
public enum AlertStatus {
    Unread(1),
    Read(2);
    private final int val;
    private AlertStatus(int v) { val = v; }
    
    // Mapping from integer to InventoryStatus
    private static final Map<Integer, AlertStatus> _map = new HashMap<Integer, AlertStatus>();
    static
    {
        for (AlertStatus status : AlertStatus.values())
            _map.put(status.val, status);
    }
 
    /**
     * Get AlertStatus from value
     * @param value Value
     * @return AlertStatus
     */
    public static AlertStatus from(int value)
    {
        return _map.get(value);
    }
    public static int to(AlertStatus as)
    {
        return as.val;
    }
}
