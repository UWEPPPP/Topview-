package controller;


import entity.po.User;
import service.Factory;
import service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author LiuJiaHui
 */
@WebServlet("/login")
@MultipartConfig
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()
                ) {
            LoginService loginService = Factory.getLoginService();
            try {
                User login = loginService.login(username, password);
                if(login==null){
                    resp.sendRedirect("login.html");
                }else {
                    req.getSession().setAttribute("user",login);
                    resp.sendRedirect("index.jsp");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
