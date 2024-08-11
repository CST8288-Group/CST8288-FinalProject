package com.FWRP.servlet;

import com.FWRP.controller.UserType;
import com.FWRP.dao.NotificationDAO;
import com.FWRP.dto.NotificationDTO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/DeleteAlert")
public class DeleteAlert extends HttpServlet {

    private ServletContext context;

    @Override
    public void init() throws ServletException {
        super.init();
        context = getServletContext();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        if (session == null
            || session.getAttribute("username") == null
            || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        if (session.getAttribute("userType") == UserType.Retailer) {
            response.sendRedirect("home.jsp");
            return;
        }
        int userId = ((Long)session.getAttribute("userId")).intValue();
        int notificationId = Integer.parseInt(request.getParameter("id"));
        NotificationDTO notification = new NotificationDTO();
        notification.setId(notificationId);
        notification.setUserId(userId);
        NotificationDAO notDao = new NotificationDAO(context);
        notDao.deleteNotification(notification);
        response.sendRedirect("alerts.jsp");
    }
}
