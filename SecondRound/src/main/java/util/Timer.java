package util;

import service.wrapper.NftMarket;

import java.math.BigInteger;
import java.util.concurrent.*;

/**
 * 计时器
 *
 * @author 刘家辉
 * @date 2023/05/10
 */
public class Timer {
    private final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("auction-thread");
            return thread;
        }
    };
    private final ExecutorService SERVICE = new ThreadPoolExecutor(
            10, 20,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            THREAD_FACTORY,
            (r, executor) -> System.out.println("线程池已满,拒绝执行")
    );
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
                nftMarket.auctionEnd(BigInteger.valueOf(id));
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        SERVICE.submit(runnable);
    }

    public static class TimerHolder {
        private static final Timer TIMER = new Timer();
    }
}