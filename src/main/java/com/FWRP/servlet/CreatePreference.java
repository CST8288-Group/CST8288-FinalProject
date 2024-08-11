package com.FWRP.servlet;

import com.FWRP.controller.UserType;
import com.FWRP.dao.PreferredFoodDAO;
import com.FWRP.dto.PreferredFoodDTO;
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
@WebServlet(name = "CreatePreference", urlPatterns = { "/CreatePreference" })
public class CreatePreference extends HttpServlet {


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
        int foodItemId = Integer.parseInt(request.getParameter("foodItemId"));

        PreferredFoodDTO prefDTO = new PreferredFoodDTO(-1, userId.intValue(), foodItemId);
        prefDTO.setId(-1);
        prefDTO.setFoodItemid(foodItemId);
        prefDTO.setUserId(userId.intValue());

        PreferredFoodDAO prefDAO = new PreferredFoodDAO(context);
        prefDAO.addPreferredFood(prefDTO);
        response.sendRedirect("preferences.jsp");
    }
}
