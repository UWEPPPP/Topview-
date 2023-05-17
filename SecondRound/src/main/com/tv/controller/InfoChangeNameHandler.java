package tv.controller;

import tv.entity.po.User;
import tv.service.IInfoService;
import tv.spring.AutoWired;
import tv.spring.CommonLogger;
import tv.spring.Component;
import tv.spring.Scope;
import tv.util.CastUtil;
import tv.util.exception.InputException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 信息变更处理程序名称
 *
 * @author 刘家辉
 * @date 2023/05/17
 */
@Component
@Scope("singleton")
@CommonLogger
public class InfoChangeNameHandler implements ServletHandler {
    @AutoWired
    public IInfoService infoServiceImpl;

    @Override
    public Object handle(HttpServletRequest request) throws InputException, SQLException, IOException, ClassNotFoundException {
        String parameter = request.getParameter("name");
        User user = CastUtil.cast(request.getSession().getAttribute("user"));
        if (parameter == null) {
            throw new InputException("输入为空");
        }
        String name = infoServiceImpl.changeInfo(parameter, null, user.getContract_address());
        user.setName(name);
        request.getSession().setAttribute("user", user);
        return "success";
    }
}
