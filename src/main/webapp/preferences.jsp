<%-- 
    Document   : preferences.jsp
    Created on : Aug. 6, 2024, 6:25:35 p.m.
    Author     : walter
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.FWRP.controller.*" %>
<%@ page import="com.FWRP.dto.*" %>
<%@ page import="com.FWRP.dao.*" %>
<%@ page import="java.util.ArrayList" %>
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
        <title>User Preferences</title>
    </head>
    <body>
        <nav>
            <a href="home.jsp">home</a>
        </nav>
        <table>
            <h2>Food Preferences</h2>
            <tr>
                <th>Item Name</th>
                <th>Remove</th>
            </tr>
            <%
                ServletContext context = getServletContext();
                UserDTO user = new UserDTO();
                Long userId = (Long)session.getAttribute("userId");
                user.setId(userId);
                PreferredFoodDAO dao = new PreferredFoodDAO(context);

                int i = 0;
                for (FoodItemDTO foodItem : dao.getPreferredFood(user)) {
                    i++;
                    out.println("<tr>");
                    out.println("<td>"+ foodItem.getName() +"</td>");
                    out.println("<td></td>");
                    out.println("</tr>");
                }
            %>
        </table>
    </body>
</html>
