package com.FWRP.servlet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    // Retrieve database connection parameters from context parameters
    private String jdbcUrl = null;
    private String jdbcUser = null;
    private String jdbcPassword = null;

    @Override
    public void init() throws ServletException {
        jdbcUrl = getServletContext().getInitParameter("jdbcUrl");
        jdbcUser = getServletContext().getInitParameter("jdbcUser");
        jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("MySQL JDBC Driver not found", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword)) {
            String sql = "SELECT * FROM user";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            out.println("<html><body>");
            out.println("<h1>Users List</h1>");
            out.println("<ul>");
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                out.println("<li>" + username + "</li>");
            }
            out.println("</ul>");
            out.println("</body></html>");
        } catch (SQLException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
