<%-- 
    Document   : transfers
    Created on : Aug. 6, 2024, 6:25:48 p.m.
    Author     : walter
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.FWRP.controller.*" %>
<%@ page import="com.FWRP.dto.*" %>
<%@ page import="com.FWRP.dao.*" %>
<%
    if (session == null
        || session.getAttribute("username") == null
        || session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    UserType userType = (UserType) session.getAttribute("userType");
    if (userType != UserType.Consumer && userType != UserType.Charity) {
        response.sendRedirect("home.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transactions Page</title>
    </head>
    <body>
        <nav>
            <a href="home.jsp">home</a>
        </nav>
        <h2><%= session.getAttribute("username") %>'s transactions</h2>
        <table>
            <tr>
                <th>Item Name</th>
                <th>Quantity</th>
                <th>Expiration</th>
                <% if (userType==UserType.Consumer) { out.println("<th>Unit Price</th>"); } %>
                <th>Time Placed</th>
                <th>Retailer Name</th>
                <th>Retailer Location</th>
            </tr>
            <%
                
                ServletContext context = getServletContext();
                UserDTO user = new UserDTO();
                Long userId = (Long)session.getAttribute("userId");
                user.setId(userId);
                TransactionsDAO txDAO = new TransactionsDAO(context);
                LocationDAO locDAO = new LocationDAO(context);
                RetailerDAO retDAO = new RetailerDAO(context);
                FoodItemDAO foodDAO = new FoodItemDAO(context);
                for (TransactionDTO tx : txDAO.getTransactionsForUser(user)) {
                    out.println("<tr>");
                    
                    FoodItemDTO foodItem = foodDAO.retrieve(tx.getInventory().getFoodItemId());
                    out.println("<td>"+ foodItem.getName()+"</td>");
                    out.println("<td>"+ tx.getQuantity() +"</td>");
                    out.println("<td>"+ tx.getInventory().getExpiration() +"</td>");
                    if (userType==UserType.Consumer) {
                        out.println("<td>"+ tx.getPrice() +"</td>");
                    }
                    out.println("<td>"+ tx.getDatePlaced() +"</td>");
                    RetailerDTO retailer = retDAO.retrieve(tx.getInventory().getRetailerId());
                    LocationDTO location = locDAO.retrieve(retailer.getLocationId());
                    out.println("<td>"+ retailer.getName() +"</td>");
                    out.println("<td>"+ location.getName() +"</td>");
                    out.println("</tr>");
                }
            %>
        </table>
    </body>
</html>
