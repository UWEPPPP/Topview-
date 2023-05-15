package tv.util;

import tv.factory.Factory;
import tv.service.wrapper.NftMarket;

import java.util.logging.Level;

/**
 * 计时器
 *
 * @author 刘家辉
 * @date 2023/05/10
 */
public class Timer {

    private final NftMarket nftMarket = Contract.getAdmin();

    private Timer() {
    }

    public static Timer getInstance() {
        return TimerHolder.TIMER;
    }

    public void beginAuction(int id, int time) {
        Runnable runnable = () -> {
            try {
                Thread.sleep(time * 1000L);
                Factory.getInstance().getAuctionService().auctionEnd(id, nftMarket);
            } catch (Exception e) {
              Logger.logException(Level.SEVERE, "拍卖结束失败", e);
            }
        };
        ThreadPool.SERVICE.submit(runnable);
    }

    public static class TimerHolder {
        private static final Timer TIMER = new Timer();
    }
}