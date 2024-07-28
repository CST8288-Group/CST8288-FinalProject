package com.FWRP.utils;

import java.sql.SQLException;
import java.sql.Connection;
import jakarta.servlet.ServletContext;

public class DBConnection {
    private static Connection connection = null;
    private String driverString = "com.mysql.cj.jdbc.Driver";

    private DBConnection(ServletContext context) {
        String jdbcUrl = context.getInitParameter("jdbcUrl");
        String jdbcUser = context.getInitParameter("jdbcUser");
        String jdbcPassword = context.getInitParameter("jdbcPassword");
        try {
            Class.forName(driverString);
            connection = java.sql.DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(ServletContext context) {
        try {
            if (connection == null || connection.isClosed()) {
                new DBConnection(context);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

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
