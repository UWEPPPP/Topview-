package tv.controller.handler;

import tv.controller.ServletHandler;
import tv.entity.bo.AuctionBidBo;
import tv.entity.po.User;
import tv.service.IAuctionBidService;
import tv.service.IAuctionService;
import tv.service.wrapper.NftMarket;
import tv.spring.annotate.AutoWired;
import tv.spring.annotate.Component;
import tv.spring.annotate.Controller;
import tv.spring.annotate.Scope;
import tv.util.CastUtil;
import tv.util.DataBinder;

import javax.servlet.http.HttpServletRequest;

/**
 * 拍卖开始处理程序
 *
 * @author 刘家辉
 * @date 2023/05/17
 */

@Component
@Scope("singleton")
@Controller
public class AuctionBidHandler implements ServletHandler {
    @AutoWired
    public IAuctionBidService auctionBidServiceImpl;

    @Override
    public Object handle(HttpServletRequest request) throws Exception {
        AuctionBidBo bind = DataBinder.bind(AuctionBidBo.class, request);
        NftMarket nftMarket = CastUtil.cast(request.getSession().getAttribute("nftMarket"));
        User user = CastUtil.cast(request.getSession().getAttribute("user"));
        int offer = auctionBidServiceImpl.offer(bind, user.getContract_address(), nftMarket);
        if (offer == CHECK) {
            throw new RuntimeException("出价失败");
        }
        return null;
    }
}
