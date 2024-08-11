<%-- 
    Document   : alerts
    Created on : Aug. 6, 2024, 6:26:00 p.m.
    Author     : walter
--%>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.FWRP.controller.*" %>
<%@ page import="com.FWRP.dto.*" %>
<%@ page import="com.FWRP.dao.*" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.temporal.ChronoUnit" %>
<%
    if (session == null
        || session.getAttribute("username") == null
        || session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    UserType userType = (UserType) session.getAttribute("userType");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alerts Page</title>
    </head>
    <body>
        <nav>
            <a href="home.jsp">home</a>
        </nav>
        <h2>Alerts</h2>
        <table>
            <tr>
                <th>Alert Time</th>
                <th>Item Name</th>
                <th>Quantity</th>
                <th>Days Remaining</th>
                <%
                    if (userType == UserType.Consumer) {
                        out.println("<th>Discounted Price</th>");
                    }
                %>
                <th>Retailer</th>
                <th>Location</th>
                <th>Mark Read</th>
                <th>Delete</th>
            </tr>
            <%
                
                ServletContext context = getServletContext();
                UserDTO user = new UserDTO();
                Long userId = (Long)session.getAttribute("userId");
                user.setId(userId);
                NotificationDAO notifyDAO = new NotificationDAO(context);
                LocationDAO locDAO = new LocationDAO(context);
                // Get the current time
                LocalDate now = LocalDate.now();
                int i = 0;
                for (NotificationDTO alert : notifyDAO.getNotificationsForUser(user)) {
                    i++;
                    InventoryDTO inv = alert.getInventory();
                    String w;
                    String _w;
                    switch (AlertStatus.from(alert.getStatus())) {
                    case Unread:
                        w="<b>";
                        _w="</b>";
                        break;
                    default:
                        w="";
                        _w="";
                    }
                    out.println("<tr>");

                    out.println("<td>"+w+ alert.getTimestamp() +_w+"</td>");
                    out.println("<td>"+w+ inv.getFoodItem().getName() +_w+"</td>");
                    out.println("<td>"+w+ inv.getQuantity() +_w+"</td>");
                    LocalDate expDate = inv.getExpiration().toLocalDate();
                    // Calculate the difference in days
                    long daysBetween = ChronoUnit.DAYS.between(now, expDate);
                    out.println("<td>"+w+ daysBetween +_w+"</td>");
                    if (userType == UserType.Consumer) {
                       out.println("<td>"+w+ inv.getDiscountedPrice()+_w+"</td>");
                    }
                    LocationDTO location = locDAO.retrieve(inv.getRetailer().getLocationId());
                    out.println("<td>"+w+inv.getRetailer().getName()+_w+"</td>");
                    out.println("<td>"+w+location.getName()+_w+"</td>");
                    out.println("<td><form id=\"readFrm"+i+"\" action=\"ReadAlert\" method=\"POST\">");
                    out.println("<input type=\"hidden\" name=\"id\" value=\"" + alert.getId()+"\"></input>");
                    out.print("<input type=\"submit\" value=\"Mark Read\"></form></td>");
                    out.println("<td><form id=\"clearFrm"+i+"\" action=\"DeleteAlert\" method=\"POST\">");
                    out.println("<input type=\"hidden\" name=\"id\" value=\"" + alert.getId()+"\"></input>");
                    out.print("<input type=\"submit\" value=\"Clear\"></form></td>");
                    out.println("</tr>");
                }
            %>
        </table>
    </body>
</html>
