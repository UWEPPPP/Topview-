package tv.controller.handler;

import tv.controller.ServletHandler;
import tv.entity.po.User;
import tv.service.IInfoService;
import tv.spring.annotate.AutoWired;
import tv.spring.annotate.Component;
import tv.spring.annotate.Controller;
import tv.spring.annotate.Scope;
import tv.util.CastUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 信息变更处理程序名称
 *
 * @author 刘家辉
 * @date 2023/05/17
 */
@Component
@Scope("singleton")
@Controller
public class InfoChangeNameHandler implements ServletHandler {
    @AutoWired
    public IInfoService infoServiceImpl;

    @Override
    public Object handle(HttpServletRequest request) throws Exception {
        String parameter = request.getParameter("name");
        User user = CastUtil.cast(request.getSession().getAttribute("user"));
        String name = infoServiceImpl.changeInfo(parameter, null, user.getContract_address());
        user.setName(name);
        request.getSession().setAttribute("user", user);
        return null;
    }
}
