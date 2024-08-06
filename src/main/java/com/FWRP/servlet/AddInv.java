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
        userDTO.setId(userId);

        String itemName = request.getParameter("itemname");
        String quantity = request.getParameter("quantity");
        String expiry = request.getParameter("expiration");
        String status = request.getParameter("status");
        String price = request.getParameter("discountPrice");
        InventoryDTO invDTO = new InventoryDTO();
        FoodItemDTO fiDTO = new FoodItemDTO();
        FoodItemDAO foodDao = new FoodItemDAO(context);
        fiDTO.setName(itemName);
        foodDao.getOrCreate(fiDTO);
        invDTO.setId(-1);
        invDTO.setFoodItem(fiDTO);
        invDTO.setQuantity(Integer.parseInt(quantity));
        invDTO.setRetailerId(userId.intValue());
        invDTO.setStatus(Integer.parseInt(status));
        String[] yyyymmdd = expiry.split("-");
        invDTO.setExpiration(new Date(Integer.parseInt(yyyymmdd[0])-1900,
                Integer.parseInt(yyyymmdd[1])-1,
                Integer.parseInt(yyyymmdd[2])));
        if (price != null && !price.isEmpty() && !price.isBlank()) {
            invDTO.setDiscountedPrice(new BigDecimal(price));
        } else {
            invDTO.setDiscountedPrice(null);
        }
        InventoryDAO invDAO = new InventoryDAO(context);
        invDAO.addInventoryFood(invDTO);
        response.sendRedirect("inventory.jsp");
    }
}
