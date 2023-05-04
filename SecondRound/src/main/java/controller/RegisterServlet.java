package controller;

import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import service.FactoryService;
import service.RegisterService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
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
        // 验证表单数据
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                avatarPart == null) {
            response.sendRedirect("src/webapp/register.html");
            return;
        }
        RegisterService registerService = FactoryService.getRegisterService();
        try {
            int register = registerService.register(username, password, avatarPart);
            response.setStatus(register);
        } catch (ContractException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}