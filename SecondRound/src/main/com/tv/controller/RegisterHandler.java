package tv.controller;

import tv.service.IRegisterService;
import tv.spring.AutoWired;
import tv.spring.CommonLogger;
import tv.spring.Component;
import tv.spring.Scope;
import tv.util.exception.InputException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 * 注册处理程序
 *
 * @author 刘家辉
 * @date 2023/05/17
 */

@Component
@Scope("singleton")
@CommonLogger
public class RegisterHandler implements ServletHandler{
    @AutoWired
    public IRegisterService registerServiceImpl;
    @Override
    public Object handle(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("UTF-8");
        // 获取提交的表单数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Part avatarPart = request.getPart("avatar");
        // 验证表单数据
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                avatarPart == null) {
            throw new InputException("注册参数异常");
        }
       int register = registerServiceImpl.register(username, password, avatarPart);
        if (register == CHECK) {
            throw new InputException("注册失败");
        }
        return "success";
    }
}
