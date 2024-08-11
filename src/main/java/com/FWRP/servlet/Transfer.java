/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.FWRP.servlet;

import com.FWRP.controller.UserType;
import com.FWRP.dao.TransactionsDAO;
import com.FWRP.dto.InventoryDTO;
import com.FWRP.dto.UserDTO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author walter
 */
@WebServlet(name = "Transfer", urlPatterns = { "/Transfer" })
public class Transfer extends HttpServlet {

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
        userDTO.setId(userId);
        userDTO.setType(UserType.to((UserType)session.getAttribute("userType")));

        String strInventoryId = request.getParameter("id");
        String strQuantity = request.getParameter("desiredQuantity");
        int inventoryId = Integer.parseInt(strInventoryId);
        int quantity = Integer.parseInt(strQuantity);
        
        if (quantity > 0 && inventoryId > 0) {
            InventoryDTO invDTO = new InventoryDTO();
            invDTO.setId(inventoryId);
            TransactionsDAO txDao = new TransactionsDAO(context);
            txDao.transferItems(userDTO, invDTO, quantity);
        }
        response.sendRedirect("transfers.jsp");
    }
}
