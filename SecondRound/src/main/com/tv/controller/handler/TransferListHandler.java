package tv.controller.handler;

import tv.controller.ServletHandler;
import tv.entity.po.User;
import tv.service.ITransferService;
import tv.spring.annotate.AutoWired;
import tv.spring.annotate.Component;
import tv.spring.annotate.Controller;
import tv.spring.annotate.Scope;
import tv.util.CastUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 转会名单处理程序
 *
 * @author 刘家辉
 * @date 2023/05/17
 */

@Component
@Scope("singleton")
@Controller
public class TransferListHandler implements ServletHandler {
    @AutoWired
    public ITransferService transferServiceImpl;

    @Override
    public Object handle(HttpServletRequest request) throws Exception {
        User user = CastUtil.cast(request.getSession().getAttribute("user"));
        return transferServiceImpl.getTransferList(user.getContract_address());
    }
}
