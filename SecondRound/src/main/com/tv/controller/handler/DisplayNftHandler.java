package tv.controller.handler;

import tv.controller.ServletHandler;
import tv.entity.po.User;
import tv.service.IDisplayService;
import tv.spring.annotate.AutoWired;
import tv.spring.annotate.Component;
import tv.spring.annotate.Controller;
import tv.spring.annotate.Scope;
import tv.util.CastUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 非功能性测试显示处理程序
 *
 * @author 刘家辉
 * @date 2023/05/17
 */

@Component
@Scope("singleton")
@Controller
public class DisplayNftHandler implements ServletHandler {
    @AutoWired
    public IDisplayService displayServiceImpl;

    @Override
    public Object handle(HttpServletRequest request) throws Exception {
        String choice = request.getParameter("choice");
        User user = CastUtil.cast(request.getSession().getAttribute("user"));
        if (choice != null) {
            return displayServiceImpl.display(choice);
        } else {
            return displayServiceImpl.displayByUser(user.getContract_address());
        }
    }
}
