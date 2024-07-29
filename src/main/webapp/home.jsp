<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <title>Home</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2>Welcome, <%= session.getAttribute("username") %>!</h2>
        <% 
        Integer userType = (Integer) session.getAttribute("userType");
        if (userType != null) {
            switch (userType) {
                case 1:
                    out.println("<p>Food Retailer</p>");
                    break;
                case 2:
                    out.println("<p>Consumer</p>");
                    break;
                case 3:
                    out.println("<p>Charitable Organization</p>");
                    break;
                default:
                    out.println("<p>Unknown User Type</p>");
                    break;
            }
        } else {
            out.println("<p>Unknown User Type</p>");
        }
    %>
        <p>This is your home page.</p>
        <a class="btn btn-danger" href="logout">Logout</a>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
