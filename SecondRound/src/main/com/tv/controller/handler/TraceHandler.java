package tv.controller.handler;

import tv.controller.ServletHandler;
import tv.service.ITraceService;
import tv.service.wrapper.NftMarket;
import tv.spring.AutoWired;
import tv.spring.Component;
import tv.spring.Controller;
import tv.spring.Scope;
import tv.util.CastUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 跟踪处理程序
 *
 * @author 刘家辉
 * @date 2023/05/17
 */

@Component
@Scope("singleton")
@Controller
public class TraceHandler implements ServletHandler {
    @AutoWired
    public ITraceService traceServiceImpl;
    @Override
    public Object handle(HttpServletRequest request) throws Exception {
        String cid = request.getParameter("cid");
        NftMarket nftMarket = CastUtil.cast(request.getSession().getAttribute("nftMarket"));
        return traceServiceImpl.getLife(cid, nftMarket);
    }
}
