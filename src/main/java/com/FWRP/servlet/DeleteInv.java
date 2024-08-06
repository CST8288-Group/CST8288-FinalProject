package com.FWRP.servlet;

import com.FWRP.controller.UserType;
import com.FWRP.dao.FoodItemDAO;
import com.FWRP.dao.InventoryDAO;
import com.FWRP.dto.FoodItemDTO;
import com.FWRP.dto.InventoryDTO;
import java.io.IOException;
import java.sql.Date;
import com.FWRP.dto.UserDTO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;

@WebServlet("/DeleteInv")
public class DeleteInv extends HttpServlet {

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
        if (session.getAttribute("userType") != UserType.Retailer) {
            response.sendRedirect("home.jsp");
        }
        Long userId = (Long)session.getAttribute("userId");
        userDTO.setId(userId);

        String itemid = request.getParameter("id");
        InventoryDTO invDTO = new InventoryDTO();
        invDTO.setId(Integer.parseInt(itemid));
        
        InventoryDAO invDAO = new InventoryDAO(context);
        invDAO.deleteInventory(invDTO);
        response.sendRedirect("inventory.jsp");
    }
}
