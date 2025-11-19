package cn.org.ultronai.firmament.admin.biz.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.org.ultronai.firmament.admin.biz.kline.KlineService;
import cn.org.ultronai.firmament.commonapi.model.CryptoTypeEnum;
import cn.org.ultronai.firmament.commonapi.model.TimeUnitEnum;
import cn.org.ultronai.firmament.support.indicator.model.OHLCV;

/**
 * kline 仓储单例实现
 * @author icanci
 * @since 1.0 Created in 2025/11/13 16:04
 */
public class KlineRepositoryHolder {
    private static final KlineRepository klineRepository = new KlineRepositoryImpl();

    /**
     * 尝试初始化数据
     *
     * @param exchange       exchange
     * @param cryptocurrency cryptocurrency
     * @param timeUnit       timeUnit
     */
    public static void tryInit(String exchange, CryptoTypeEnum cryptocurrency, TimeUnitEnum timeUnit) {
        KlineCacheManager manager = klineRepository.getManager(exchange, cryptocurrency.name(), timeUnit);
        if (manager.isInitialized()) {
            return;
        }
        if (!manager.isInitialized()) {
            // 查询数据进行初始化
            // 查询300条数据初始化
            List<OHLCV> ohlcvs = KlineService.fetchOHLCVForEnd(exchange, cryptocurrency, timeUnit);
            klineRepository.save(exchange, cryptocurrency.name(), timeUnit, ohlcvs);
            manager.initialized();
        }
    }

    /**
    * 尝试初始化数据
    *
    * @param exchange       exchange
    * @param cryptocurrency cryptocurrency
    * @param timeUnit       timeUnit
    */
    public static void save(String exchange, CryptoTypeEnum cryptocurrency, TimeUnitEnum timeUnit) {
        // 获取最新的10条数据，即可满足条件
        List<OHLCV> ohlcvs = KlineService.fetchOHLCVLimitForEnd(exchange, cryptocurrency, timeUnit, 10);
        klineRepository.save(exchange, cryptocurrency.name(), timeUnit, ohlcvs);
    }

    /**
     * 获取排序后的kline数据: 根据开盘时间倒排
     *
     * @param exchange       exchange
     * @param cryptocurrency cryptocurrency
     * @param timeUnit       timeUnit
     * @return klineData
     */
    public static List<OHLCV> getSortedKlineData(String exchange, String cryptocurrency, TimeUnitEnum timeUnit) {
        return klineRepository.getSortedKlineData(exchange, cryptocurrency, timeUnit);
    }

    /**
     * 获取排序后的kline数据: 根据开盘时间倒排取第一个
     *
     * @param exchange       exchange
     * @param cryptocurrency cryptocurrency
     * @param timeUnit       timeUnit
     * @return klineData
     */
    public static OHLCV getLatestKlineData(String exchange, String cryptocurrency, TimeUnitEnum timeUnit) {
        return klineRepository.getLatestKlineData(exchange, cryptocurrency, timeUnit);
    }

    /**
     * 获取多个排序后的kline数据: 根据开盘时间倒排
     *
     * @param exchange         exchange
     * @param cryptocurrencies cryptocurrencies
     * @param timeUnit         timeUnit
     * @return klineData
     */
    public static Map<String, List<OHLCV>> getSortedKlineData(String exchange, Set<String> cryptocurrencies, TimeUnitEnum timeUnit) {
        return klineRepository.getSortedKlineData(exchange, cryptocurrencies, timeUnit);
    }

    /**
     * 删除kline数据
     *
     * @param exchange       exchange
     * @param coinsToDelete  coinsToDelete
     * @param timeUnit       timeUnit
     */
    public static void remove(String exchange, Set<CryptoTypeEnum> coinsToDelete, TimeUnitEnum timeUnit) {
        klineRepository.remove(exchange, coinsToDelete, timeUnit);
    }
}
