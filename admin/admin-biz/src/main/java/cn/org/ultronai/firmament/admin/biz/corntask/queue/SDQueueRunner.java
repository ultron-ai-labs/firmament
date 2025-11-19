package cn.org.ultronai.firmament.admin.biz.corntask.queue;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Sets;

import cn.org.ultronai.firmament.admin.biz.repository.KlineRepositoryHolder;
import cn.org.ultronai.firmament.commonapi.model.CryptoTypeEnum;
import cn.org.ultronai.firmament.commonapi.model.TimeUnitEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 异同队列模型
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/13 17:42
 */
@Slf4j
@AllArgsConstructor
public class SDQueueRunner implements Runnable {
    /**
     * 币种
     */
    private final SDQueueKey                 sdQueueKey;
    /**
     * 异同队列模型：策略处理
     */
    private final Set<SDQueueStrategyRunner> runners = new CopyOnWriteArraySet<>();

    /**
     * 策略执行器
     */
    @Override
    public void run() {
        try {
            // 交易所
            String exchange = sdQueueKey.getExchange();
            // 时间周期
            TimeUnitEnum timeUnit = sdQueueKey.getTimeUnit();
            // 先更新数据
            Set<CryptoTypeEnum> currentCryptoTypes = currentCryptoTypes();
            for (CryptoTypeEnum currentCryptoType : currentCryptoTypes) {
                // 尝试更新全量数据
                KlineRepositoryHolder.tryInit(exchange, currentCryptoType, timeUnit);
            }
            // 币种
            for (CryptoTypeEnum currentCryptoType : currentCryptoTypes) {
                // 更新数据 10条数据
                KlineRepositoryHolder.save(exchange, currentCryptoType, timeUnit);
            }
            // 运行策略
            for (SDQueueStrategyRunner runner : runners) {
                SDQueueRunnerPoolHolder.execute(runner);
            }
        } catch (Exception e) {
            log.error("SDQueueRunner error", e);
        }
    }

    public void addRunner(SDQueueStrategyRunner runner) {
        runners.add(runner);
    }

    public void removeRunner(SDQueueStrategyRunner runner) {
        boolean remove = runners.removeIf(r -> StringUtils.equals(r.getRealTimeStrategy().getStrategyId(), runner.getRealTimeStrategy().getStrategyId()));
        // 移除成功
        if (remove) {
            // 清理缓存的数据
            Set<CryptoTypeEnum> deletedCoins = runner.getCryptoTypes();
            // 获取当前任务币种的数据
            Set<CryptoTypeEnum> currentCoins = currentCryptoTypes();
            // 如果当前币种的数据为 A、B，而删除的币种为C，则将A、B保存下来，将C删除
            Set<CryptoTypeEnum> coinsToDelete = Sets.newHashSet();
            // 确定要删除的币种数据
            for (CryptoTypeEnum coin : deletedCoins) {
                if (!currentCoins.contains(coin)) {
                    coinsToDelete.add(coin);
                }
            }

            // 删除币种数据
            KlineRepositoryHolder.remove(sdQueueKey.getExchange(), coinsToDelete, sdQueueKey.getTimeUnit());
        }
    }

    public boolean isEmpty() {
        return runners.isEmpty();
    }

    private Set<CryptoTypeEnum> currentCryptoTypes() {
        return runners.stream().map(SDQueueStrategyRunner::getCryptoTypes).flatMap(Set::stream).collect(Collectors.toSet());
    }

    /**
     * 将sdQueueKey交易所和timeUnit的数据清空
     */
    public void clear() {
        Set<CryptoTypeEnum> clearCoins = Arrays.stream(CryptoTypeEnum.values()).collect(Collectors.toSet());
        KlineRepositoryHolder.remove(sdQueueKey.getExchange(), clearCoins, sdQueueKey.getTimeUnit());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        SDQueueRunner that = (SDQueueRunner) object;
        return Objects.equals(sdQueueKey, that.sdQueueKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sdQueueKey);
    }
}
