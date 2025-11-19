package cn.org.ultronai.firmament.admin.biz.corntask.strategy;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import cn.org.ultronai.firmament.admin.biz.corntask.queue.SDQueueDynamicTaskService;
import cn.org.ultronai.firmament.admin.biz.corntask.queue.SDQueueStrategyRunner;
import cn.org.ultronai.firmament.admin.biz.service.AITradingService;
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.RealTimeStrategyDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.RealTimeStrategyDO;
import cn.org.ultronai.firmament.commonapi.model.TimeUnitEnum;

/**
 * 策略任务启动回调：启动时加载任务
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/08 18:53
 */
@Service
public class RealTimeStrategyTaskStartCallback implements ApplicationContextAware {
    @Resource
    private SDQueueDynamicTaskService sdQueueDynamicTaskService;
    @Resource
    private RealTimeStrategyDAO       realTimeStrategyDAO;
    @Resource
    private AITradingService          aiTradingService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 启动的时候加载任务
        // 启动一个线程加载
        new Thread(() -> {
            List<RealTimeStrategyDO> realTimeStrategies = realTimeStrategyDAO.queryAll();
            for (RealTimeStrategyDO realTimeStrategy : realTimeStrategies) {
                if (!StringUtils.equals(realTimeStrategy.getStrategyState(), "running")) {
                    continue;
                }

                // 正在运行的任务
                sdQueueDynamicTaskService.addOrUpdateTask(realTimeStrategy.getExchange(), TimeUnitEnum.valueOf(realTimeStrategy.getTimeUnit()),
                    new SDQueueStrategyRunner(aiTradingService, realTimeStrategy));
            }
        }).start();
    }
}
