package tv.controller.handler;

import tv.controller.ServletHandler;
import tv.service.IAuctionService;
import tv.spring.*;

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
    public IAuctionService auctionServiceImpl;
    @Override
    public Object handle(HttpServletRequest request) throws Exception {
            return auctionServiceImpl.showAuction();
    }
}
