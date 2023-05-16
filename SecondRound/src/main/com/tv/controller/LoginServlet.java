package tv.controller;


import com.alibaba.fastjson.JSON;
import tv.entity.po.User;
import tv.factory.Factory;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import tv.service.ILoginService;
import tv.service.wrapper.NftMarket;
import tv.util.CastUtil;
import tv.util.Logger;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * @author LiuJiaHui
 */
@WebServlet("/Servlet/login")
@MultipartConfig
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        User user = CastUtil.cast(session.getAttribute("user"));
        NftMarket nftMarket = CastUtil.cast(session.getAttribute("nftMarket"));
        String profile = user.getProfile();
        int balance;
        try {
             balance = Factory.getInstance().getInfoService().changeBalance(nftMarket);
        } catch (SQLException | ClassNotFoundException | ContractException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> message = new HashMap<>(2);
        message.put("image", profile);
        message.put("name", user.getName());
        message.put("balance",balance);
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
            Logger.info("用户名或密码为空");
            resp.sendRedirect("/login.html?fail");
        } else {
            ILoginService service = Factory.getInstance().getLoginService();
            try {
                Map<String, Object> login = service.login(username, password);
                if (login == null) {
                    resp.sendRedirect("/login.html?fail");
                } else {
                    req.getSession().setAttribute("user", login.get("user"));
                    req.getSession().setAttribute("nftMarket", login.get("nftMarket"));
                    resp.sendRedirect("/personal-info.html");
                }
            } catch (Exception e) {
                Logger.logException(Level.WARNING,"登录异常", e);
            }

        }
    }
}
