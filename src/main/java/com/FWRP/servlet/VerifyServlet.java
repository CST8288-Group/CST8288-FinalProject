package com.FWRP.servlet;

import java.io.IOException;
import java.sql.SQLException;
import com.FWRP.dao.UserDao;
import com.FWRP.dto.User;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/verify")
public class VerifyServlet extends HttpServlet {

    private UserDao userDao;
    private ServletContext context;

    @Override
    public void init() throws ServletException {
        super.init();
        context = getServletContext();
        userDao = new UserDao(context);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");

        try {
            User user = userDao.findByVerificationToken(token);
            if (user != null) {
                userDao.verifyUser(user);
                response.sendRedirect("verify-success.jsp");
            } else {
                response.sendRedirect("verify-failure.jsp");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
