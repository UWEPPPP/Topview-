package tv.controller.handler;

import tv.controller.ServletHandler;
import tv.entity.bo.AuctionBeginBo;
import tv.service.IAuctionService;
import tv.service.wrapper.NftMarket;
import tv.spring.AutoWired;
import tv.spring.Component;
import tv.spring.Controller;
import tv.spring.Scope;
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
public class AuctionBeginHandler implements ServletHandler {
    @AutoWired
    public IAuctionService auctionServiceImpl;
    @Override
    public Object handle(HttpServletRequest request) throws Exception {
        NftMarket user = CastUtil.cast(request.getSession().getAttribute("nftMarket"));
        AuctionBeginBo bind = DataBinder.bind(AuctionBeginBo.class, request);
        int status = auctionServiceImpl.auctionBegin(bind, user);
        if (status == CHECK) {
            throw new RuntimeException("开启拍卖失败");
        }
        return null;
    }
}
