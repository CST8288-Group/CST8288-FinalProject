/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.FWRP.servlet;

import com.FWRP.controller.UserType;
import com.FWRP.dao.LocationDAO;
import com.FWRP.dao.RetailerDAO;
import com.FWRP.dto.LocationDTO;
import com.FWRP.dto.RetailerDTO;
import com.FWRP.dto.UserDTO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
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
@WebServlet(name = "RetailerUpdate", urlPatterns = { "/RetailerUpdate" })
public class RetailerUpdate extends HttpServlet {

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

        String retailerName = request.getParameter("retailerName");
        String locationName = request.getParameter("location");
        
        LocationDAO locDAO = new LocationDAO(context);
        LocationDTO location = locDAO.getOrCreate(locationName);
        
        RetailerDTO retailer = new RetailerDTO();
        retailer.setUserId(userId.intValue());
        retailer.setName(retailerName);
        retailer.setLocationId(location.getId());
        
        RetailerDAO retailerDAO = new RetailerDAO(context);
        retailerDAO.update(retailer);

        response.sendRedirect("RetailerSettings.jsp");
    }
}
