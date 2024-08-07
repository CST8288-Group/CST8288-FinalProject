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
public enum NotificationStatus {
    Unread(1),
    Read(2);
    private final int val;
    private NotificationStatus(int v) { val = v; }
    
    // Mapping from integer to NotificationStatus
    private static final Map<Integer, NotificationStatus> _map = new HashMap<Integer, NotificationStatus>();
    static
    {
        for (NotificationStatus status : NotificationStatus.values())
            _map.put(status.val, status);
    }
 
    /**
     * Get NotificationStatus from value
     * @param value Value
     * @return NotificationStatus
     */
    public static NotificationStatus from(int value)
    {
        return _map.get(value);
    }
    public static int to(NotificationStatus ns)
    {
        return ns.val;
    }
}
