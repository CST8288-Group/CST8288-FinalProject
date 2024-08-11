package com.FWRP.servlet;

import com.FWRP.controller.UserType;
import com.FWRP.dao.FoodItemDAO;
import com.FWRP.dao.InventoryDAO;
import com.FWRP.dao.RetailerDAO;
import com.FWRP.dto.FoodItemDTO;
import com.FWRP.dto.InventoryDTO;
import com.FWRP.dto.RetailerDTO;
import java.io.IOException;
import com.FWRP.dto.UserDTO;
import com.FWRP.utils.FormParser;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/addItem")
public class AddInv extends HttpServlet {

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

        RetailerDTO retailer = getRetailedDTO(userId.intValue());
        if (retailer == null) {
            response.sendRedirect("login.jsp");
        }

        FoodItemDTO fiDTO = getFoodItemDTO(request.getParameter("itemname"));

        InventoryDTO invDTO = new InventoryDTO(-1,
                Integer.parseInt(request.getParameter("quantity")),
                FormParser.parseExpiry(request.getParameter("expiration")),
                Integer.parseInt(request.getParameter("status")),
                FormParser.parseNullablePrice(request.getParameter("discountPrice")),
                fiDTO,
                retailer);

        InventoryDAO invDAO = new InventoryDAO(context);
        invDAO.addInventoryFood(invDTO);
        response.sendRedirect("inventory.jsp");
    }

    private RetailerDTO getRetailedDTO(int userId) {
        RetailerDTO retailer = new RetailerDTO();
        retailer.setUserId(userId);
        return new RetailerDAO(context).retrieve(retailer);
    }

    private FoodItemDTO getFoodItemDTO(String foodName) {
        FoodItemDTO fiDTO = new FoodItemDTO();
        fiDTO.setName(foodName);
        return new FoodItemDAO(context).getOrCreate(fiDTO);
    }
}
