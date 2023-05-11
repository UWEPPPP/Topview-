package util;

import factory.FactoryService;
import service.wrapper.NftMarket;

import java.util.concurrent.*;

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
                FactoryService.getAuctionService().auctionEnd(id, nftMarket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        ThreadPool.SERVICE.submit(runnable);
    }

    public static class TimerHolder {
        private static final Timer TIMER = new Timer();
    }
}