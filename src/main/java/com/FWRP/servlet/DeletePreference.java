package com.FWRP.servlet;

import com.FWRP.controller.UserType;
import com.FWRP.dao.PreferredFoodDAO;

import java.io.IOException;

import com.FWRP.dto.PreferredFoodDTO;
import com.FWRP.dto.UserDTO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author walter
 */
@WebServlet("/DeletePreference")
public class DeletePreference extends HttpServlet {

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

        int prefId = Integer.parseInt(request.getParameter("prefId"));
        int foodItemId = Integer.parseInt(request.getParameter("foodItemId"));

        PreferredFoodDTO prefDTO = new PreferredFoodDTO(prefId, userId.intValue(), foodItemId);

        PreferredFoodDAO prefDAO = new PreferredFoodDAO(context);
        prefDAO.deletePreferredFood(prefDTO);
        response.sendRedirect("preferences.jsp");
    }
}
