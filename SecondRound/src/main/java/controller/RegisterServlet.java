package controller;

import service.Factory;
import service.RegisterService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/register")
@MultipartConfig
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("test");
        // 获取提交的表单数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Part avatarPart = request.getPart("avatar");
        System.out.println("testTwo");
        // 验证表单数据
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                avatarPart == null) {
            response.sendRedirect("register.html");
            return;
        }
        System.out.println("TestThree");
        // 保存用户头像到服务器
        String avatarFileName = Paths.get(avatarPart.getSubmittedFileName()).getFileName().toString();
        RegisterService registerService = Factory.getRegisterService();
        registerService.register(username, password, avatarFileName);
        // 重定向到登录页面
        response.sendRedirect("login.html");
    }
}