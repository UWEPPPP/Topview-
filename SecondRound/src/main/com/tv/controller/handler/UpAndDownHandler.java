package tv.controller.handler;

import tv.controller.ServletHandler;
import tv.service.IInfoService;
import tv.spring.*;
import tv.util.exception.InputException;

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
        String cid = request.getParameter("cid");
        String onSale = request.getParameter("onSale");
        if (cid == null || onSale == null) {
            throw new InputException("输入为空");
        }
            int status = infoServiceImpl.upAndDown(cid, onSale);
        if(status == CHECK) {
            throw new InputException("上架/下架失败");
        }
        return null;
    }
}
