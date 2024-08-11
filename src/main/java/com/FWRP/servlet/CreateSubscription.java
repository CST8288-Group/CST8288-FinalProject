package com.FWRP.servlet;

import com.FWRP.controller.UserType;
import com.FWRP.dao.SubscriptionDAO;
import com.FWRP.dto.LocationDTO;
import com.FWRP.dto.SubscriptionDTO;
import com.FWRP.dto.UserDTO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 *
 * @author walter
 */
@WebServlet("/CreateSubscription")
public class CreateSubscription extends HttpServlet {

    private ServletContext context;

    @Override
    public void init() throws ServletException {
        super.init();
        context = getServletContext();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDTO userDTO = new UserDTO();
        HttpSession session = request.getSession();

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
        
        Long userId = (Long)session.getAttribute("userId");
        int locationId = Integer.parseInt(request.getParameter("locationId"));

        LocationDTO locDTO = new LocationDTO();
        locDTO.setId(locationId);
        SubscriptionDTO subDTO = new SubscriptionDTO(-1, userId.intValue(), locDTO);

        SubscriptionDAO subDAO = new SubscriptionDAO(context);
        subDAO.addSubscription(subDTO);
        response.sendRedirect("preferences.jsp");
    }
}
