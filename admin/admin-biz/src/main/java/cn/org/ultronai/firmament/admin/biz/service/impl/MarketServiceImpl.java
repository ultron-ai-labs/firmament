package cn.org.ultronai.firmament.admin.biz.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;

import cn.org.ultronai.firmament.admin.biz.kline.KlineService;
import cn.org.ultronai.firmament.admin.biz.prompt.model.MarketInfo;
import cn.org.ultronai.firmament.admin.biz.service.MarketService;
import cn.org.ultronai.firmament.commonapi.model.TimeUnitEnum;
import cn.org.ultronai.firmament.support.indicator.TempIndicatorApiTools;
import cn.org.ultronai.firmament.support.indicator.model.OHLCV;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 19:28
 */
@Service("marketService")
public class MarketServiceImpl implements MarketService {

    /**
     * queryMarketInfo
     *
     * @param marketOHLCV marketOHLCV
     * @return List<MarketInfo>
     */
    @Override
    public List<MarketInfo> queryMarketInfo(Map<String, List<OHLCV>> marketOHLCV, String exchange, TimeUnitEnum timeUnit) {

        List<MarketInfo> marketInfos = new java.util.ArrayList<>();

        Set<Map.Entry<String, List<OHLCV>>> entrySet = marketOHLCV.entrySet();
        for (Map.Entry<String, List<OHLCV>> entry : entrySet) {
            String cryptocurrency = entry.getKey();
            List<OHLCV> klineData = entry.getValue();
            klineData.sort((o1, o2) -> (int) (o2.getOpenTime() - o1.getOpenTime()));
            OHLCV last = klineData.iterator().next();

            MarketInfo marketInfo = new MarketInfo();
            marketInfo.setCoin(cryptocurrency);
            marketInfo.setTimestamp(timeUnit.getRealTimeUnit(exchange));
            marketInfo.setOpen(BigDecimal.valueOf(last.getOpen()));
            marketInfo.setClose(BigDecimal.valueOf(last.getClose()));
            marketInfo.setHigh(BigDecimal.valueOf(last.getHigh()));
            marketInfo.setLow(BigDecimal.valueOf(last.getLow()));
            marketInfo.setVol(BigDecimal.valueOf(last.getVolume()));
            marketInfo.setIndicators(TempIndicatorApiTools.calculateIndicators(cryptocurrency, klineData));
            marketInfos.add(marketInfo);
        }
        return marketInfos;
    }

    /**
     * 查询最新价格数据
     *
     * @param exchange         exchange
     * @param cryptocurrencies cryptocurrencies
     */
    @Override
    public Map<String, BigDecimal> queryNewestMarketInfo(String exchange, Set<String> cryptocurrencies) {
        return KlineService.fetchOHLCVForRealTime(exchange, cryptocurrencies);
    }

    /**
     * 查询最新价格数据
     *
     * @param exchange       exchange
     * @param cryptocurrency cryptocurrency
     */
    @Override
    public BigDecimal queryNewestMarketInfo(String exchange, String cryptocurrency) {
        return KlineService.fetchOHLCVForRealTime(exchange, Sets.newHashSet(cryptocurrency)).get(cryptocurrency);
    }
}
