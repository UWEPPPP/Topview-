package util;

import java.util.concurrent.*;

/**
 * 线程池
 *
 * @author 刘家辉
 * @date 2023/05/12
 */
public class ThreadPool {
    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("auction-thread");
            return thread;
        }
    };
    public static final ExecutorService SERVICE = new ThreadPoolExecutor(
            10, 20,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            THREAD_FACTORY,
            (r, executor) -> Logger.info("线程池已满，拒绝任务")
    );
}
