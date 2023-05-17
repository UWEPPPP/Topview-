package tv.controller;

import tv.service.IAuctionService;
import tv.service.wrapper.NftMarket;
import tv.spring.AutoWired;
import tv.spring.CommonLogger;
import tv.spring.Component;
import tv.spring.Scope;
import tv.util.CastUtil;
import tv.util.exception.InputException;

import javax.servlet.http.HttpServletRequest;

/**
 * 拍卖开始处理程序
 *
 * @author 刘家辉
 * @date 2023/05/17
 */

@Component
@Scope("singleton")
@CommonLogger
public class AuctionBeginHandler implements ServletHandler{
    @AutoWired
    public IAuctionService auctionServiceImpl;
    @Override
    public Object handle(HttpServletRequest request) throws Exception {
        String cid = request.getParameter("cid");
        String duration = request.getParameter("duration");
        String amount = request.getParameter("amount");
        NftMarket user = CastUtil.cast(request.getSession().getAttribute("nftMarket"));
        if (cid == null || duration == null || amount == null) {
            throw new InputException("输入为空");
        }
       int status = auctionServiceImpl.auctionBegin(cid, duration, amount, user);
        if (status == CHECK) {
            throw new InputException("开启拍卖失败");
        }
        return "success";
    }
}
