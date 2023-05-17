package tv.controller;


import tv.service.ILoginService;
import tv.spring.AutoWired;
import tv.spring.CommonLogger;
import tv.spring.Component;
import tv.spring.Scope;
import tv.util.exception.InputException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author LiuJiaHui
 */

@Component
@Scope("singleton")
@CommonLogger
public class LoginHandler implements ServletHandler {
    @AutoWired
    public ILoginService loginServiceImpl;
    @Override
    public Object handle(HttpServletRequest request) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()
        ) {
            throw new InputException("用户名或密码为空");
        }
            Map<String, Object> login = loginServiceImpl.login(username, password);
            if (login == null) {
               throw new InputException("用户名或密码错误");
            } else {
                request.getSession().setAttribute("user", login.get("user"));
                request.getSession().setAttribute("nftMarket", login.get("nftMarket"));
            }
        return "success login";
    }
}
