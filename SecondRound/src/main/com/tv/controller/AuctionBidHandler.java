package tv.controller;

import tv.entity.po.User;
import tv.service.IAuctionService;
import tv.service.wrapper.NftMarket;
import tv.spring.AutoWired;
import tv.spring.CommonLogger;
import tv.spring.Component;
import tv.spring.Scope;
import tv.util.CastUtil;
import tv.util.exception.InputException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * 拍卖开始处理程序
 *
 * @author 刘家辉
 * @date 2023/05/17
 */

@Component
@Scope("singleton")
@CommonLogger
public class AuctionBidHandler implements ServletHandler{
    @AutoWired
    public IAuctionService auctionServiceImpl;
    @Override
    public Object handle(HttpServletRequest request) throws SQLException, ClassNotFoundException, InterruptedException, InputException {
        String nfdId = request.getParameter("nftId");
        String bidPrice = request.getParameter("bidPrice");
        NftMarket nftMarket= CastUtil.cast(request.getSession().getAttribute("nftMarket"));
        User user= CastUtil.cast(request.getSession().getAttribute("user"));
        int offer = auctionServiceImpl.offer(Integer.parseInt(nfdId.trim()), Integer.parseInt(bidPrice.trim()), user.getContract_address(), nftMarket);
        if (offer == CHECK) {
            throw new InputException("出价失败");
        }
        return "success";
    }
}
