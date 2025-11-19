package cn.org.ultronai.firmament.admin.biz.corntask.queue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 队列执行线程池
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/13 18:48
 */
public class SDQueueRunnerPoolHolder {
    private static final ThreadPoolExecutor ORDER_POOL = new ThreadPoolExecutor(40, //
        120, //
        60L, //
        TimeUnit.SECONDS, //
        new LinkedBlockingQueue<>(2000), //
        runnable -> new Thread(runnable, "SDQueueRunnerPoolHolder Pool-" + runnable.hashCode()), //
        (r, executor) -> {
            r.run();
        });

    // 执行任务
    public static void execute(SDQueueStrategyRunner runner) {
        ORDER_POOL.execute(runner);
    }

}
