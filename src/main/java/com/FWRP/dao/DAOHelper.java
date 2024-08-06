/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.FWRP.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;

/**
 *
 * @author walter
 */
public class DAOHelper {
    /**
     * Helper function that logs all SQL warnings.
     * @param con The connection to the SQL database.
     * @throws SQLException on error communicating with database.
     */
    static public void logAndClearSQLWarnings(String source, Connection con) throws SQLException {
        SQLWarning sqlWarn = con.getWarnings();
        // Loop over all generated warnings.
        while (sqlWarn != null) {
            System.out.println(source+" Warning: SQL: " + sqlWarn.toString());
            sqlWarn = sqlWarn.getNextWarning();
        }
        con.clearWarnings();
    }
}
