package cn.org.ultronai.firmament.admin.biz.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.org.ultronai.firmament.commonapi.model.CryptoTypeEnum;
import cn.org.ultronai.firmament.commonapi.model.TimeUnitEnum;
import cn.org.ultronai.firmament.support.indicator.model.OHLCV;

/**
 * kline 仓储顶级接口
 * @author icanci
 * @since 1.0 Created in 2025/11/13 15:37
 */
interface KlineRepository {
    /**
     * 保存kline数据
     * 
     * @param exchange exchange
     * @param cryptocurrency cryptocurrency
     * @param timeUnit timeUnit
     * @param klineData klineData
     */
    void save(String exchange, String cryptocurrency, TimeUnitEnum timeUnit, List<OHLCV> klineData);

    /**
     * 获取排序后的kline数据: 根据开盘时间倒排
     *
     * @param exchange exchange
     * @param cryptocurrency cryptocurrency
     * @param timeUnit timeUnit
     * @return klineData
     */
    List<OHLCV> getSortedKlineData(String exchange, String cryptocurrency, TimeUnitEnum timeUnit);

    /**
     * 获取多个排序后的kline数据: 根据开盘时间倒排
     * @param exchange exchange
     * @param cryptocurrencies cryptocurrencies
     * @param timeUnit timeUnit
     * @return klineData
     */
    Map<String, List<OHLCV>> getSortedKlineData(String exchange, Set<String> cryptocurrencies, TimeUnitEnum timeUnit);

    /**
     * 删除kline数据
     * @param exchange 交易所
     * @param coinsToDelete 币种
     * @param timeUnit 时间单位
     */
    void remove(String exchange, Set<CryptoTypeEnum> coinsToDelete, TimeUnitEnum timeUnit);

    /**
     * 是否有管理器
     *
     * @param exchange 交易所
     * @param cryptocurrency 币种
     * @param timeUnit 时间单位
     * @return kline数据缓存
     */
    KlineCacheManager getManager(String exchange, String cryptocurrency, TimeUnitEnum timeUnit);

    /**
     * 获取最新的kline数据
     *
     * @param exchange 交易所
     * @param cryptocurrency 币种
     * @param timeUnit 时间单位
     * @return kline数据
     */
    OHLCV getLatestKlineData(String exchange, String cryptocurrency, TimeUnitEnum timeUnit);
}
