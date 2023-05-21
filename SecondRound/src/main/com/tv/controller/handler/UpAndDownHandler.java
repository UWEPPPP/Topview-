package tv.controller.handler;

import tv.controller.ServletHandler;
import tv.entity.bo.UpAndDownBo;
import tv.service.IInfoService;
import tv.spring.annotate.AutoWired;
import tv.spring.annotate.Component;
import tv.spring.annotate.Controller;
import tv.spring.annotate.Scope;
import tv.util.DataBinder;

import javax.servlet.http.HttpServletRequest;

/**
 * 上下处理程序
 *
 * @author 刘家辉
 * @date 2023/05/17
 */

@Component
@Scope("singleton")
@Controller
public class UpAndDownHandler implements ServletHandler {
    @AutoWired
    public IInfoService infoServiceImpl;

    @Override
    public Object handle(HttpServletRequest request) throws Exception {
        UpAndDownBo bind = DataBinder.bind(UpAndDownBo.class, request);
        int status = infoServiceImpl.upAndDown(bind);
        if (status == CHECK) {
            throw new RuntimeException("上架/下架失败");
        }
        return null;
    }
}
