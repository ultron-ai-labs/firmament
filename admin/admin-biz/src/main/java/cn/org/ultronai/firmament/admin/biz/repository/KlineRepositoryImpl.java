package cn.org.ultronai.firmament.admin.biz.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.org.ultronai.firmament.commonapi.model.CryptoTypeEnum;
import cn.org.ultronai.firmament.commonapi.model.ExchangeTypeEnum;
import cn.org.ultronai.firmament.commonapi.model.TimeUnitEnum;
import cn.org.ultronai.firmament.support.indicator.model.OHLCV;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/13 15:51
 */
class KlineRepositoryImpl implements KlineRepository {
    /**
     * kline数据缓存
     */
    private static final Map<KlineManagerKey, KlineCacheManager> KLINE_REPOSITORY = new HashMap<>();
    /**
     * 初始化缓存
     */
    static {
        TimeUnitEnum[] timeUnit = TimeUnitEnum.values();
        for (TimeUnitEnum timeUnitEnum : timeUnit) {
            for (CryptoTypeEnum cryptoType : CryptoTypeEnum.values()) {
                ExchangeTypeEnum[] exchangeType = ExchangeTypeEnum.values();
                for (ExchangeTypeEnum exchangeTypeEnum : exchangeType) {
                    KlineManagerKey key = new KlineManagerKey(cryptoType.name(), timeUnitEnum, exchangeTypeEnum.getType());
                    KLINE_REPOSITORY.put(key, new KlineCacheManager());
                }
            }
        }
    }

    /**
     * 保存kline数据
     *
     * @param exchange       exchange
     * @param cryptocurrency cryptocurrency
     * @param timeUnit       timeUnit
     * @param klineData      klineData
     */
    @Override
    public void save(String exchange, String cryptocurrency, TimeUnitEnum timeUnit, List<OHLCV> klineData) {
        KlineManagerKey key = new KlineManagerKey(cryptocurrency, timeUnit, exchange);
        KlineCacheManager manager = KLINE_REPOSITORY.get(key);
        if (manager == null) {
            throw new RuntimeException("KlineRepositoryImpl.save: key not found." + key);
        }
        for (OHLCV kline : klineData) {
            manager.add(kline);
        }
    }

    /**
     * 获取排序后的kline数据: 根据开盘时间倒排
     *
     * @param exchange       exchange
     * @param cryptocurrency cryptocurrency
     * @param timeUnit       timeUnit
     * @return klineData
     */
    @Override
    public List<OHLCV> getSortedKlineData(String exchange, String cryptocurrency, TimeUnitEnum timeUnit) {
        KlineManagerKey key = new KlineManagerKey(cryptocurrency, timeUnit, exchange);
        KlineCacheManager manager = KLINE_REPOSITORY.get(key);
        if (manager == null) {
            throw new RuntimeException("KlineRepositoryImpl.save: key not found." + key);
        }
        return manager.getAll();
    }

    /**
     * 获取多个排序后的kline数据: 根据开盘时间倒排
     *
     * @param exchange         exchange
     * @param cryptocurrencies cryptocurrencies
     * @param timeUnit         timeUnit
     * @return klineData
     */
    @Override
    public Map<String, List<OHLCV>> getSortedKlineData(String exchange, Set<String> cryptocurrencies, TimeUnitEnum timeUnit) {
        Map<String, List<OHLCV>> result = new HashMap<>();
        for (String cryptocurrency : cryptocurrencies) {
            KlineManagerKey key = new KlineManagerKey(cryptocurrency, timeUnit, exchange);
            KlineCacheManager manager = KLINE_REPOSITORY.get(key);
            if (manager == null) {
                throw new RuntimeException("KlineRepositoryImpl.save: key not found." + key);
            }
            result.put(cryptocurrency, manager.getAll());
        }
        return result;
    }

    /**
     * 删除kline数据
     *
     * @param exchange      交易所
     * @param coinsToDelete 币种
     * @param timeUnit      时间单位
     */
    @Override
    public void remove(String exchange, Set<CryptoTypeEnum> coinsToDelete, TimeUnitEnum timeUnit) {
        for (CryptoTypeEnum coin : coinsToDelete) {
            KlineManagerKey key = new KlineManagerKey(coin.name(), timeUnit, exchange);
            KlineCacheManager manager = KLINE_REPOSITORY.get(key);
            if (manager != null) {
                manager.clear();
            }
        }
    }

    /**
     * 是否有管理器
     *
     * @param exchange       交易所
     * @param cryptocurrency 币种
     * @param timeUnit       时间单位
     * @return kline数据缓存
     */
    @Override
    public KlineCacheManager getManager(String exchange, String cryptocurrency, TimeUnitEnum timeUnit) {
        KlineManagerKey key = new KlineManagerKey(cryptocurrency, timeUnit, exchange);
        KlineCacheManager klineCacheManager = KLINE_REPOSITORY.get(key);
        if (klineCacheManager == null) {
            throw new RuntimeException("KlineRepositoryImpl.getManager: key not found." + key);
        }
        return klineCacheManager;
    }

    /**
     * 获取最新的kline数据
     *
     * @param exchange       交易所
     * @param cryptocurrency 币种
     * @param timeUnit       时间单位
     * @return kline数据
     */
    @Override
    public OHLCV getLatestKlineData(String exchange, String cryptocurrency, TimeUnitEnum timeUnit) {
        KlineManagerKey key = new KlineManagerKey(cryptocurrency, timeUnit, exchange);
        KlineCacheManager klineCacheManager = KLINE_REPOSITORY.get(key);
        if (klineCacheManager == null) {
            throw new RuntimeException("KlineRepositoryImpl.getLatestKlineData: key not found." + key);
        }
        return klineCacheManager.getAll().get(0);
    }
}
