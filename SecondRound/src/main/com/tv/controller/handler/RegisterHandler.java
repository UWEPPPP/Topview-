package tv.controller.handler;

import tv.controller.ServletHandler;
import tv.entity.bo.RegisterBo;
import tv.service.IRegisterService;
import tv.spring.AutoWired;
import tv.spring.Component;
import tv.spring.Controller;
import tv.spring.Scope;
import tv.util.DataBinder;

import javax.servlet.http.HttpServletRequest;

/**
 * 注册处理程序
 *
 * @author 刘家辉
 * @date 2023/05/17
 */

@Component
@Scope("singleton")
@Controller
public class RegisterHandler implements ServletHandler {
    @AutoWired
    public IRegisterService registerServiceImpl;

    @Override
    public Object handle(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("UTF-8");
        RegisterBo bind = DataBinder.bind(RegisterBo.class, request);
        int register = registerServiceImpl.register(bind);
        if (register == CHECK) {
            throw new RuntimeException("注册失败");
        }
        return null;
    }
}
