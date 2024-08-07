<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.FWRP.controller.UserType" %>
<%
    
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FWRP Home</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2>Welcome, <%= session.getAttribute("username") %>!</h2>
        <% 
        UserType userType = (UserType) session.getAttribute("userType");
        if (userType != null) {
            switch (userType) {
                case Retailer:
                    out.println("<p>User Type: Food Retailer</p>");
                    out.println("<p><a href=\"\">Set Location</a></p>");
                    out.println("<p><a href=\"additemform.jsp\">Add Item</a></p>");
                    out.println("<p><a href=\"inventory.jsp\">Inventory</a></p>");
                    out.println("<p><a href=\"expiring.jsp\">Expiring items</a></p>");
                    break;
                case Consumer:
                    out.println("<p>User Type: Consumer</p>");
                    out.println("<p><a href=\"available.jsp\">View Discounted Food</a></p>");
                    out.println("<p><a href=\"transfers.jsp\">View Purchased Food</a></p>");
                    out.println("<p><a href=\"alerts.jsp\">View Alerts</a></p>");
                    out.println("<p><a href=\"\">Change Alerts Subscriptions</a></p>");
                    break;
                case Charity:
                    out.println("<p>User Type: Charitable Organization</p>");
                    out.println("<p><a href=\"available.jsp\">View Food Donations</a></p>");
                    out.println("<p><a href=\"transfers.jsp\">View Claimed Food</a></p>");
                    out.println("<p><a href=\"alerts.jsp\">View Alerts</a></p>");
                    out.println("<p><a href=\"alertsettings.jsp\">Change Alerts Subscriptions</a></p>");
                    break;
                default:
                    out.println("<p>Unknown User Type</p>");
                    break;
            }
        } else {
            out.println("<p>Unknown User Type</p>");
        }
    %>
        <a class="btn btn-danger" href="logout">Logout</a>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
