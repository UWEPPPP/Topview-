package tv.util;

import tv.factory.HandlerFactory;
import tv.service.IAuctionService;
import tv.service.wrapper.NftMarket;
import tv.spring.AutoWired;
import tv.spring.Component;
import tv.spring.Scope;

import java.util.logging.Level;

/**
 * 计时器
 *
 * @author 刘家辉
 * @date 2023/05/10
 */
@Component
@Scope("singleton")
public class Timer {
    @AutoWired
    public IAuctionService auctionService;

    private final NftMarket nftMarket = Contract.getAdmin();

    public void beginAuction(int id, int time) {
        Runnable runnable = () -> {
            try {
                Thread.sleep(time * 1000L);
                auctionService.auctionEnd(id, nftMarket);
            } catch (Exception e) {
              Logger.logException(Level.SEVERE, "拍卖结束失败", e);
            }
        };
        ThreadPool.SERVICE.submit(runnable);
    }
}