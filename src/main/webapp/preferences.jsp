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
            <h2>Manage Food Preferences</h2>
            <p> Pick from the food items from the drop down, and click Create Preference for new
            alerts and emails when
            <%= ((UserType) session.getAttribute("userType")) == UserType.Consumer ? "discounts":"donations" %>
            become available.
             </p>
                <%
                    ServletContext context = getServletContext();
                    FoodItemDAO foodDAO = new FoodItemDAO(context);
                    out.println("<form id=\"createFP\" action=\"CreatePreference\" method=\"POST\">");
                    out.println("<select name=\"foodItemId\">");
                    for (FoodItemDTO foodItem : foodDAO.getAllFoodItems()) {
                        out.println("<option value=\"" + foodItem.getId() +"\">" + foodItem.getName() +"</option>");
                    }
                    out.println("</select>");

                    out.println("<input type=\"submit\" value=\"Create Food Preference\"></form>");
                %>
            <tr>
                <th>Item Name</th>
                <th>Remove</th>
            </tr>
            <%
                UserDTO user = new UserDTO();
                Long userId = (Long)session.getAttribute("userId");
                user.setId(userId);
                PreferredFoodDAO prefDAO = new PreferredFoodDAO(context);

                int i = 0;
                for (PreferredFoodDTO prefDTO : prefDAO.getPreferredFoods(user)) {
                    i++;
                    FoodItemDTO foodItem = foodDAO.retrieve(prefDTO.getFoodItemId());
                    out.println("<tr>");
                    out.println("<td>"+ foodItem.getName() +"</td>");
                    out.println("<td><form id=\"delPref"+i+"\" action=\"DeletePreference\" method=\"POST\">");
                    out.println("<input type=\"hidden\" name=\"prefId\" value=\"" + prefDTO.getId()+"\" />");
                    out.println("<input type=\"hidden\" name=\"foodItemId\" value=\"" + foodItem.getId()+"\" />");
                    out.println("<input type=\"submit\" value=\"Delete\"></form></td>");
                    out.println("</tr>");
                }
            %>
        </table>
        <h2>Manage Location Subscriptions:</h2>
        <table>
            <tr>
                <th>Location</th>
                <th>Delete</th>
            </tr>
            <%
                SubscriptionDAO subDao = new SubscriptionDAO(context);
                i = 0;
                for (SubscriptionDTO sub : subDao.getSubscriptionsForUser(user)) {
                    i++;
                    out.println("<tr>");
                    out.println("<td>" + sub.getLocation().getName() + "</td>");
                    out.println("<td><form id=\"delSub"+i+"\" action=\"DeleteSubscription\" method=\"POST\">");
                    out.println("<input type=\"hidden\" name=\"id\" value=\"" + sub.getId()+"\" />");
                    out.println("<input type=\"hidden\" name=\"locationId\" value=\"" + sub.getLocation().getId()+"\" />");
                    out.println("<input type=\"submit\" value=\"Delete\"></form></td>");
                    out.println("</tr>");
                }
            %>
        </table>
        <h2>Create Location Subscription</h2>
        <p> Pick from the retailer locations from the drop down, and click Create Subscription for
        alerts and emails when
        <%= ((UserType) session.getAttribute("userType")) == UserType.Consumer ? "discounts":"donations" %>
        become available.
         </p>
            <%
                LocationDAO locDao = new LocationDAO(context);
                out.println("<form id=\"create\" action=\"CreateSubscription\" method=\"POST\">");
                out.println("<select name=\"locationId\">");
                for (LocationDTO location : locDao.getAllLocations()) {
                    out.println("<option value=\"" + location.getId() +"\">" + location.getName() +"</option>");
                }
                out.println("</select>");

                out.println("<input type=\"submit\" value=\"Create Subscription\"></form>");
            %>
    </body>
</html>
