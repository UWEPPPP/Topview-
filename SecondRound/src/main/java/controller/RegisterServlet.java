package controller;

import org.apache.commons.io.IOUtils;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import service.Factory;
import service.RegisterService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * @author LiuJiaHui
 */
@WebServlet("/register")
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
        System.out.println(username+"|"+password+"|"+avatarPart);
        // 验证表单数据
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                avatarPart == null) {
            response.sendRedirect("src/webapp/register.html");
            return;
        }
        // 保存用户头像到服务器
        InputStream inputStream = avatarPart.getInputStream();
        byte[] byteArray = IOUtils.toByteArray(inputStream);

        RegisterService registerService = Factory.getRegisterService();
        try {
            registerService.register(username, password,byteArray);
        } catch (ContractException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        inputStream.close();
        // 重定向到登录页面
        response.sendRedirect("/login.html");
    }
}