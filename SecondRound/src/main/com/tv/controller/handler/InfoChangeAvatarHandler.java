package tv.controller.handler;

import tv.controller.ServletHandler;
import tv.entity.po.User;
import tv.service.IInfoService;
import tv.spring.annotate.AutoWired;
import tv.spring.annotate.Component;
import tv.spring.annotate.Controller;
import tv.spring.annotate.Scope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 * 信息改变配置文件处理程序
 *
 * @author 刘家辉
 * @date 2023/05/17
 */

@Component
@Scope("singleton")
@Controller
public class InfoChangeAvatarHandler implements ServletHandler {
    @AutoWired
    public IInfoService infoServiceImpl;

    @Override
    public Object handle(HttpServletRequest request) throws Exception {
        Part part = request.getPart("file");
        User user = (User) request.getSession().getAttribute("user");
        String profile = infoServiceImpl.changeInfo(null, part, user.getContract_address());
        user.setProfile(profile);
        request.getSession().setAttribute("user", user);
        return null;
    }
}
