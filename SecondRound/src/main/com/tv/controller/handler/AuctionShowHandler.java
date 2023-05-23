package tv.controller.handler;

import tv.controller.ServletHandler;
import tv.service.IAuctionBidService;
import tv.service.IAuctionService;
import tv.spring.annotate.AutoWired;
import tv.spring.annotate.Component;
import tv.spring.annotate.Controller;
import tv.spring.annotate.Scope;

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
public class AuctionShowHandler implements ServletHandler {
    @AutoWired
    public IAuctionBidService auctionBidServiceImpl;

    @Override
    public Object handle(HttpServletRequest request) throws Exception {
        return auctionBidServiceImpl.showAuction();
    }
}
