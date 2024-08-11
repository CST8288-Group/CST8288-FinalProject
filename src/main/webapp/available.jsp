<%-- 
    Document   : available.jsp
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
        <title>Available Food</title>
    </head>
    <body>
        <nav>
            <a href="home.jsp">home</a>
        </nav>
        <table>
            <tr>
                <th>Item Name</th>
                <th>Quantity Available</th>
                <th>Expiration</th>
            <%
                if (userType == UserType.Charity) {
                    out.println("<th>Desired Quantity</th>");
                    out.println("<th>Claim</th>");
                } else {
                    out.println("<th>Discounted Price</th>");
                    out.println("<th>Desired Quantity</th>");
                    out.println("<th>Purchase</th>");
                }
            %>
                
            </tr>
            <%
                ServletContext context = getServletContext();
                UserDTO user = new UserDTO();
                Long userId = (Long)session.getAttribute("userId");
                user.setId(userId);
                FoodItemDAO foodDAO = new FoodItemDAO(context);
                InventoryDAO invDAO = new InventoryDAO(context);
                InventoryStatus type = userType == UserType.Charity
                                       ? InventoryStatus.Donation
                                       : InventoryStatus.Discounted;
                int i = 0;
                for (InventoryDTO inv : invDAO.getAvailableFood(type)) {
                    i++;
                    out.println("<tr>");
                    out.println("<form id=\"form"+i+"\" action=\"Transfer\" method=\"POST\">");
                    out.println("<input type=\"hidden\" name=\"id\" value=\"" + inv.getId()+"\">");
                    FoodItemDTO foodItem = foodDAO.retrieve(inv.getFoodItemId());
                    out.println("<td>"+ foodItem.getName() +"</td>");
                    out.println("<td>"+ inv.getQuantity() +"</td>");
                    out.println("<td>"+ inv.getExpiration() +"</td>");
                    if (userType == UserType.Consumer) {
                       out.println("<td>"+ inv.getDiscountedPrice()+"</td>");
                    }
                    out.println("<td><input form=\"form"+i+"\" type=\"number\" "
                    + "name=\"desiredQuantity\" value=\"0\""
                    + " min=\"0\" max=\""+inv.getQuantity()+"\"></td>");
                    out.print("<td><input type=\"submit\" value=\"");
                    if (userType == UserType.Charity) {
                       out.print("Claim");
                    } else {
                        out.print("Purchase");
                    }
                    out.println("\"></td></form>");
                    out.println("</tr>");
                }
            %>
        </table>
    </body>
</html>
