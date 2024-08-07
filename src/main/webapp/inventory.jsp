<%-- 
    Document   : inventory
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
        <h2><%= session.getAttribute("username") %>'s inventory:</h2>
        <table>
            <tr>
                <th>Item Name</th>
                <th>Quantity</th>
                <th>Expiration</th>
                <th>Status</th>
                <th>Discounted Price</th>
                <th>Update</th>
                <th>Delete</th>
            </tr>
            <%
                
                ServletContext context = getServletContext();
                UserDTO user = new UserDTO();
                Long userId = (Long)session.getAttribute("userId");
                user.setId(userId);
                InventoryDAO dao = new InventoryDAO(context);
                int i = 0;
                for (InventoryDTO inv : dao.getInventoryForUser(user)) {
                    i++;
                    out.println("<tr>");
                    out.println("<form id=\"form"+i+"\" action=\"UpdateInv\" method=\"POST\"><input type=\"hidden\""
                        + " name=\"id\" value=\"" + inv.getId()+"\" />");
                    out.println("<td><input form=\"form"+i+"\" type=\"text\" "
                    + "name=\"itemname\" value=\""+ inv.getFoodItem().getName() +"\"</td>");
                    out.println("<td><input form=\"form"+i+"\" type=\"number\" "
                    + "name=\"quantity\" value=\""+ inv.getQuantity() +"\"</td>");
                    out.println("<td><input form=\"form"+i+"\" type=\"date\" "
                    + "name=\"expiration\" value=\""+ inv.getExpiration() +"\"</td>");
                    out.println("<td><select form=\"form"+i+"\" name=\"status\">"
                    + " <option value=\"1\""+(inv.getStatus()==InventoryStatus.to(InventoryStatus.Regular)?" selected":"")+">Regular</option>"
                    + " <option value=\"2\""+(inv.getStatus()==InventoryStatus.to(InventoryStatus.Donation)?" selected":"")+">For Donation</option>"
                    + " <option value=\"3\""+(inv.getStatus()==InventoryStatus.to(InventoryStatus.Discounted)?" selected":"")+">Discounted</option>"
                    +"</select></td>");
                    out.println("<td><input form=\"form"+i+"\" type=\"number\" "
                    + "name=\"price\" value=\""
                    + (inv.getDiscountedPrice()==null?"":inv.getDiscountedPrice().toString())
                    +"\" step=\".01\"></td>");
                    out.println("<td><input form=\"form"+i+"\" type=\"submit\" value=\"Update\"/></input></td>");
                    out.println("</form>");
                    out.println("<td><form id=\"del"+i+"\" action=\"DeleteInv\" method=\"POST\">"
                    + "<input type=\"hidden\" name=\"id\" value=\"" + inv.getId()+"\" />"
                    + "<input type=\"submit\" value=\"Delete\"/></input></form></td>");
                    out.println("</tr>");
                }
            %>
        </table>
    </body>
</html>
