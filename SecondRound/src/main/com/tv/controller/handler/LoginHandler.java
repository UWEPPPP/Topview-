package tv.controller.handler;


import tv.controller.ServletHandler;
import tv.entity.bo.LoginBo;
import tv.service.ILoginService;
import tv.spring.annotate.AutoWired;
import tv.spring.annotate.Component;
import tv.spring.annotate.Controller;
import tv.spring.annotate.Scope;
import tv.util.DataBinder;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author LiuJiaHui
 */

@Component
@Scope("singleton")
@Controller
public class LoginHandler implements ServletHandler {
    @AutoWired
    public ILoginService loginServiceImpl;

    @Override
    public Object handle(HttpServletRequest request) throws Exception {
        LoginBo bind = DataBinder.bind(LoginBo.class, request);
        Map<String, Object> login = loginServiceImpl.login(bind);
        if (login == null) {
            throw new RuntimeException("用户名或密码错误");
        } else {
            request.getSession().setAttribute("user", login.get("user"));
            request.getSession().setAttribute("nftMarket", login.get("nftMarket"));
        }
        return null;
    }
}
