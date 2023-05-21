package tv.controller.handler;

import tv.controller.ServletHandler;
import tv.entity.po.User;
import tv.service.IInfoService;
import tv.spring.annotate.AutoWired;
import tv.spring.annotate.Component;
import tv.spring.annotate.Controller;
import tv.spring.annotate.Scope;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 信息改变配置文件处理程序
 *
 * @author 刘家辉
 * @date 2023/05/17
 */

@Component
@Scope("singleton")
@Controller
public class InfoChangeProfileHandler implements ServletHandler {
    @AutoWired
    public IInfoService infoServiceImpl;
    @Override
    public Object handle(HttpServletRequest request) throws ServletException, IOException, SQLException, ClassNotFoundException {
        Part part = request.getPart("avatar");
        User user = (User) request.getSession().getAttribute("user");

        String profile = infoServiceImpl.changeInfo(null, part, user.getContract_address());
        user.setProfile(profile);
        request.getSession().setAttribute("user", user);
        return null;
    }
}
