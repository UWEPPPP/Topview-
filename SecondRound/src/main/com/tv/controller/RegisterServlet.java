package tv.controller;

import tv.factory.Factory;
import tv.service.IRegisterService;
import tv.util.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * @author LiuJiaHui
 */
@WebServlet("/Servlet/register")
@MultipartConfig
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        // 获取提交的表单数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Part avatarPart = request.getPart("avatar");
        // 验证表单数据
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                avatarPart == null) {
            Logger.info("注册参数异常");
            response.sendRedirect("/register.html");
            return;
        }
        IRegisterService IRegisterService = Factory.getInstance().getRegisterService();
        try {
            int register = IRegisterService.register(username, password, avatarPart);
            response.setStatus(register);
        } catch (SQLException | ClassNotFoundException | InterruptedException e) {
            Logger.logException(Level.SEVERE,"注册失败", e);
        }
    }
}