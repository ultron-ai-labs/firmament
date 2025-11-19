package cn.org.ultronai.firmament.admin.biz.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.org.ultronai.firmament.admin.biz.prompt.model.MarketInfo;
import cn.org.ultronai.firmament.commonapi.model.TimeUnitEnum;
import cn.org.ultronai.firmament.support.indicator.model.OHLCV;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 19:28
 */
public interface MarketService {

    /**
     * queryMarketInfo
     * 
     * @param marketOHLCV  marketOHLCV
     * @param timeUnit timeUnit
     * @return List<MarketInfo>
     */
    List<MarketInfo> queryMarketInfo(Map<String, List<OHLCV>> marketOHLCV, String exchange, TimeUnitEnum timeUnit);

    /**
     * 查询最新价格数据
     *
     * @param exchange exchange
     * @param cryptocurrencies cryptocurrencies
     */
    Map<String, BigDecimal> queryNewestMarketInfo(String exchange, Set<String> cryptocurrencies);

    /**
     * 查询最新价格数据
     *
     * @param exchange exchange
     * @param cryptocurrency cryptocurrency
     */
    BigDecimal queryNewestMarketInfo(String exchange, String cryptocurrency);
}
