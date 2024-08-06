package com.FWRP.servlet;

import java.io.IOException;
import java.sql.SQLException;
import com.FWRP.dao.UserDao;
import com.FWRP.dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.FWRP.utils.Mail;
import java.util.UUID;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        super.init();
        userDao = new UserDao(getServletContext()); // Ensure proper initialization if needed
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        int type = Integer.parseInt(request.getParameter("type"));
        String address = request.getParameter("address");
       


        UserDTO user = new UserDTO();
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        user.setType(type);
        user.setAddress(address);
        user.setVerificationToken(UUID.randomUUID().toString());

        try {
            userDao.registerUser(user);
            String verificationLink = request.getRequestURL().toString().replace("/register", "/verify?token=") + user.getVerificationToken();
            Mail.sendVerificationEmail(email, verificationLink);
            

            response.sendRedirect("login.jsp?msg=Registration successful. Please check your email for verification link.");
        } catch (SQLException e) {
            throw new ServletException("Database error during registration", e);
        } catch (Exception e) {
            throw new ServletException("Error sending verification email", e);
        }
    }
}

