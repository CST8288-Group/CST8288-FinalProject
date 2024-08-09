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
    <style>
        html, body {
            height: 100%;
            margin: 0;
            overflow: hidden; /* Prevents scrolling */
        }

        .background-video {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            object-fit: cover; /* Ensures the video covers the entire screen */
            z-index: -1; /* Places the video behind the content */
        }
        .container {
            background-color: rgba(255, 255, 255, 0.8); /* Optional: Adds a white background with opacity to the form */
            padding: 20px;
            border-radius: 10px;
        }

        .wrapper {
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
        }
    </style>
</head>
<body>
    <video autoplay muted loop class="background-video">
        <source src="3.webm" type="video/webm">
        Your browser does not support the video tag.
    </video>
    <div class="container mt-5">
        <h2>Welcome, <%= session.getAttribute("username") %>!</h2>
        <% 
        UserType userType = (UserType) session.getAttribute("userType");
        if (userType != null) {
            switch (userType) {
                case Retailer:
                    out.println("<p>User Type: Food Retailer</p>");
                    out.println("<p><a href=\"RetailerSettings.jsp\">Retailer Settings</a></p>");
                    out.println("<p><a href=\"additemform.jsp\">Add Item</a></p>");
                    out.println("<p><a href=\"inventory.jsp\">Inventory</a></p>");
                    out.println("<p><a href=\"expiring.jsp\">Expiring items</a></p>");
                    break;
                case Consumer:
                    out.println("<p>User Type: Consumer</p>");
                    out.println("<p><a href=\"available.jsp\">View Discounted Food</a></p>");
                    out.println("<p><a href=\"transfers.jsp\">View Purchased Food</a></p>");
                    out.println("<p><a href=\"alerts.jsp\">View Alerts</a></p>");
                    out.println("<p><a href=\"preferences.jsp\">Change Alerts Subscriptions</a></p>");
                    break;
                case Charity:
                    out.println("<p>User Type: Charitable Organization</p>");
                    out.println("<p><a href=\"available.jsp\">View Food Donations</a></p>");
                    out.println("<p><a href=\"transfers.jsp\">View Claimed Food</a></p>");
                    out.println("<p><a href=\"alerts.jsp\">View Alerts</a></p>");
                    out.println("<p><a href=\"preferences.jsp\">Change Preferences & Subscriptions</a></p>");
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
