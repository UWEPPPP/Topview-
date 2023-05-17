package tv.controller;

import tv.entity.po.User;
import tv.service.IDisplayService;
import tv.spring.AutoWired;
import tv.spring.CommonLogger;
import tv.spring.Component;
import tv.spring.Scope;
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
@CommonLogger
public class DisplayNftHandler implements ServletHandler{
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
