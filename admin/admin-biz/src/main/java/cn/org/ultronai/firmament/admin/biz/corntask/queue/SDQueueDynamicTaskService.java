package cn.org.ultronai.firmament.admin.biz.corntask.queue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import cn.org.ultronai.firmament.commonapi.model.TimeUnitEnum;

/**
 * 异同队列模型动态任务服务
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/13 17:47
 */
@Service
public class SDQueueDynamicTaskService {
    private static final int                                      CORE_SIZE        = Runtime.getRuntime().availableProcessors();

    private static final ThreadPoolTaskScheduler                  scheduler        = new ThreadPoolTaskScheduler();             //

    private final Map<SDQueueKey, ScheduledFuture<SDQueueRunner>> taskFutureMap    = new ConcurrentHashMap<>();

    private final Map<SDQueueKey, SDQueueRunner>                  sdQueueRunnerMap = new ConcurrentHashMap<>();

    public SDQueueDynamicTaskService() {
        scheduler.initialize();
    }

    static {
        // 设置线程池大小
        scheduler.setPoolSize(CORE_SIZE << 2);
        // 设置取消任务时是否立即从队列中移除[citation:5]
        scheduler.setRemoveOnCancelPolicy(true);
        // 设置线程名前缀[citation:5]
        scheduler.setThreadNamePrefix("task-");
    }

    /**
     * 添加或更新定时任务 使用Trigger方式
     */
    public boolean addOrUpdateTask(String exchange, TimeUnitEnum timeUnit, SDQueueStrategyRunner sdQueueStrategyRunner) {
        try {
            // 验证cron表达式
            CronTrigger cronTrigger = new CronTrigger(timeUnit.getCron());

            // 获取 key
            SDQueueKey sdQueueKey = new SDQueueKey(exchange, timeUnit);
            ScheduledFuture<SDQueueRunner> currentFuture = taskFutureMap.get(sdQueueKey);
            // 第一次加入的情况
            if (currentFuture == null) {
                SDQueueRunner sdQueueRunner = new SDQueueRunner(sdQueueKey);
                sdQueueRunner.addRunner(sdQueueStrategyRunner);
                ScheduledFuture<SDQueueRunner> future = (ScheduledFuture<SDQueueRunner>) scheduler.schedule(sdQueueRunner, cronTrigger);
                taskFutureMap.put(sdQueueKey, future);
                sdQueueRunnerMap.put(sdQueueKey, sdQueueRunner);
            } else {
                // 已经存在了
                SDQueueRunner sdQueueRunner = sdQueueRunnerMap.get(sdQueueKey);
                sdQueueRunner.addRunner(sdQueueStrategyRunner);
            }
            return true;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 移除定时任务
     */
    public void removeTask(String exchange, TimeUnitEnum timeUnit, SDQueueStrategyRunner sdQueueStrategyRunner) {
        try {
            // 获取 key
            SDQueueKey sdQueueKey = new SDQueueKey(exchange, timeUnit);
            ScheduledFuture<SDQueueRunner> currentFuture = taskFutureMap.get(sdQueueKey);
            // 如果缓存没有，不处理
            if (currentFuture == null) {
                return;
            }

            // 缓存有数据
            SDQueueRunner sdQueueRunner = sdQueueRunnerMap.get(sdQueueKey);
            sdQueueRunner.removeRunner(sdQueueStrategyRunner);

            if (sdQueueRunner.isEmpty()) {
                sdQueueRunner.clear();
                taskFutureMap.remove(sdQueueKey);
                sdQueueRunnerMap.remove(sdQueueKey);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 更新定时任务
     */
    public boolean updateTask(String exchange, TimeUnitEnum timeUnit, SDQueueStrategyRunner previousRunner, SDQueueStrategyRunner newRunner) {
        removeTask(exchange, timeUnit, previousRunner);
        return addOrUpdateTask(exchange, timeUnit, newRunner);
    }
}
