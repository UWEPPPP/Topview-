package controller;


import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import entity.po.User;
import service.Factory;
import service.LoginService;
import util.Ipfs;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

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
                    String profile = login.getProfile();
                    byte[] download = Ipfs.download(profile);
                    Map<String,byte[]> image = new HashMap<>(1);
                    image.put("image",download);
                    String jsonString = JSON.toJSONString(image);
                    resp.setContentType("application/json");
                    resp.getWriter().write(jsonString);
                    resp.sendRedirect("personal-info.html");
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
