package tv.controller;

import tv.entity.po.User;
import tv.service.ITransferService;
import tv.spring.AutoWired;
import tv.spring.CommonLogger;
import tv.spring.Component;
import tv.spring.Scope;
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
@CommonLogger
public class TransferListHandler implements ServletHandler {
    @AutoWired
    public ITransferService transferServiceImpl;

    @Override
    public Object handle(HttpServletRequest request) throws Exception {
        User user = CastUtil.cast(request.getSession().getAttribute("user"));
        return transferServiceImpl.getTransferList(user.getContract_address());
    }
}
