package controller;


import com.alibaba.fastjson.JSON;
import entity.po.User;
import factory.FactoryService;
import service.ILoginService;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LiuJiaHui
 */
@WebServlet("/login")
@MultipartConfig
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String profile = user.getProfile();
        Map<String, Object> message = new HashMap<>(2);
        message.put("image", profile);
        message.put("name", user.getName());
        message.put("balance", user.getBalance());
        String jsonString = JSON.toJSONString(message);
        resp.setContentType("application/json");
        resp.getWriter().write(jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println(username + " | " + password);
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()
        ) {
            resp.sendRedirect("login.html?fail");
        } else {
            ILoginService ILoginService = FactoryService.getLoginService();
            try {
                Map<String, Object> login = ILoginService.login(username, password);
                if (login == null) {
                    resp.sendRedirect("login.html?fail");
                } else {
                    req.getSession().setAttribute("user", login.get("user"));
                    req.getSession().setAttribute("nftMarket", login.get("nftMarket"));
                    resp.sendRedirect("personal-info.html");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
}
