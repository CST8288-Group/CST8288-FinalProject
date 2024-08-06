<%-- 
    Document   : additem
    Created on : Jul. 31, 2024, 10:56:14 a.m.
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
        <title>Inventory Page</title>
    </head>
    <body>
        <nav>
            <a href="home.jsp">home</a>
        </nav>
        <h2>Add Item to Inventory</h2>
        <form action="addItem" method="POST">
            <label for="name">Item name: </label>
            <input type="text" id="name" name="itemname"  required>
            <br>
            <label for="quantity">Quantity: </label>
            <input type="number" id="quantity" step="1" name="quantity"  required>
            <br>
            <label for="expiration">Expiry Date: </label>
            <input type="date" id="expiration" name="expiration" required>
            <br>
            <label for="status">Status: </label>
            <select name="status" id="status" required>
                <option value="1">Normal</option>
                <option value="2">Discount</option>
                <option value="3">Donation</option>
            </select>
            <br>
            <label for="price">Discount Price </label>
            <input type="number" id="price" step=".01" name="discountPrice"  required>
            <br>
            <input type="submit" value="Submit">
        </form>
    </body>
</html>