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
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDao userDao;
    private ServletContext context;

    @Override
    public void init() throws ServletException {
        super.init();
        context = getServletContext();
        userDao = new UserDao(context);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userDao.validateUser(username, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("username", user.getName());
                session.setAttribute("userId", user.getId());
                response.sendRedirect("home.jsp");
            } else {
                response.sendRedirect("login.jsp?error=Invalid username or password");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
