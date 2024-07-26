package com.FWRP.utils;

import java.sql.SQLException;
import java.sql.Connection;

public class DBConnection {
     private static Connection connection = null;
    
    private String driverString = "com.mysql.cj.jdbc.Driver";

    /**
     * Private constructor to initialize the database connection.
     * 
     * @param serverUrl the URL of the database server
     * @param userString the username for database authentication
     * @param passwordString the password for database authentication
     */
    private DBConnection(String serverUrl, String userString, String passwordString) {
        try {
            Class.forName(driverString);
            connection = java.sql.DriverManager.getConnection(serverUrl, userString, passwordString);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the database connection instance. If the connection is not already established,
     * it initializes a new connection.
     * 
     * @param serverUrl the URL of the database server
     * @param userString the username for database authentication
     * @param passwordString the password for database authentication
     * @return the database connection instance
     */
    public static Connection getConnection(String serverUrl, String userString, String passwordString) {
        if (connection == null) {
            new DBConnection(serverUrl, userString, passwordString);
        }
        return connection;
    }

    /**
     * Closes the database connection.
     */
    public static synchronized void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
