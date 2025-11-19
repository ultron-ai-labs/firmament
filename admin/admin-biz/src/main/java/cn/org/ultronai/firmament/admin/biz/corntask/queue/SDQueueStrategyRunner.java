package cn.org.ultronai.firmament.admin.biz.corntask.queue;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import cn.org.ultronai.firmament.admin.biz.service.AITradingService;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.RealTimeStrategyDO;
import cn.org.ultronai.firmament.commonapi.model.CryptoTypeEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 异同队列模型：策略处理
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/13 17:42
 */
@Data
@Slf4j
public class SDQueueStrategyRunner implements Runnable {
    private final AITradingService    aiTradingService;
    private final RealTimeStrategyDO  realTimeStrategy;
    private final Set<CryptoTypeEnum> cryptoTypes;

    public SDQueueStrategyRunner(AITradingService aiTradingService, RealTimeStrategyDO realTimeStrategy) {
        this.aiTradingService = aiTradingService;
        this.realTimeStrategy = realTimeStrategy;
        this.cryptoTypes = realTimeStrategy.getCryptocurrencies().stream().map(CryptoTypeEnum::valueOf).collect(Collectors.toSet());
    }

    @Override
    public void run() {
        try {
            aiTradingService.aiTrading(realTimeStrategy);
        } catch (Throwable e) {
            log.error("aiTrading error", e);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        SDQueueStrategyRunner that = (SDQueueStrategyRunner) object;
        return Objects.equals(realTimeStrategy, that.realTimeStrategy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(realTimeStrategy);
    }
}
