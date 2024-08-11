<%-- 
    Document   : RetailerSettings
    Created on : Aug. 7, 2024, 9:51:55 a.m.
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
    if (session.getAttribute("userType") != UserType.Retailer) {
        response.sendRedirect("home.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Retailer Settings</title>
    </head>
    <body>
        <nav>
            <a href="home.jsp">home</a>
        </nav>
        <h2>Retailer Settings</h2>
        <form action="RetailerUpdate" method="POST">
            <label for="name">Retailer name: </label>
            <%
                ServletContext context = getServletContext();
                UserDTO user = new UserDTO();
                Long userId = (Long)session.getAttribute("userId");
                user.setId(userId);
                RetailerDAO retailerDao = new RetailerDAO(context);
                LocationDAO locDAO = new LocationDAO(context);
                RetailerDTO retailerDto = retailerDao.retrieve(userId.intValue());
                LocationDTO location = locDAO.retrieve(retailerDto.getLocationId());
                out.print("<input type=\"text\" id=\"name\" name=\"retailerName\"");
                out.println(" value=\""+retailerDto.getName()+"\" required>");
            %>
            <br>
            <label for="location">Location </label>
            <%
                out.print("<input type=\"text\" id=\"location\" name=\"location\"");
                out.println(" value=\""+location.getName()+"\" required>");
            %>
            <br>
            <input type="submit" value="Submit">
        </form>
    </body>
</html>