package cn.org.ultronai.firmament.admin.biz.kline;

import cn.org.ultronai.firmament.commonapi.model.CryptoTypeEnum;
import cn.org.ultronai.firmament.commonapi.model.KlineData;
import cn.org.ultronai.firmament.commonapi.model.TimeUnitEnum;
import cn.org.ultronai.firmament.exchange.binance.BinanceTools;
import cn.org.ultronai.firmament.exchange.okx.OkxApiTools;
import cn.org.ultronai.firmament.support.indicator.model.OHLCV;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/13 19:03
 */
public class KlineService {
    public static List<OHLCV> fetchOHLCVForEnd(String exchange, CryptoTypeEnum cryptocurrency, TimeUnitEnum timeUnitEnum) {
        return fetchOHLCVLimitForEnd(exchange, cryptocurrency, timeUnitEnum, 301);
    }

    public static List<OHLCV> fetchOHLCVLimitForEnd(String exchange, CryptoTypeEnum cryptocurrency, TimeUnitEnum timeUnitEnum, int limit) {
        if (limit < 1) {
            throw new RuntimeException("limit must be greater than 0");
        }
        switch (exchange) {
            case "binance":
                return modelMapperForEnd(BinanceTools.getKlines(cryptocurrency, timeUnitEnum, limit));
            case "okx":
                return modelMapperForEnd(OkxApiTools.getKlines(cryptocurrency, timeUnitEnum, limit));
            default:
                throw new RuntimeException("exchange not support");
        }
    }

    public static List<OHLCV> fetchHistoryOHLCV(String exchange, CryptoTypeEnum cryptocurrency, TimeUnitEnum timeUnitEnum, long from, long to) {
        switch (exchange) {
            case "okx":
                return modelMapperForEnd(OkxApiTools.getKlinesInRange(cryptocurrency, timeUnitEnum, from, to));
            default:
                throw new RuntimeException("exchange not support");
        }
    }

    /**
     * 获取实时kline数据
     *
     * @param exchange       exchange
     * @return klineData
     */
    public static Map<String, BigDecimal> fetchOHLCVForRealTime(String exchange, Set<String> cryptocurrencies) {
        switch (exchange) {
            case "binance":
                return getNewestBinanceKlines(cryptocurrencies);
            case "okx":
                return getNewestOkxKlineData(cryptocurrencies);
            default:
                throw new RuntimeException("exchange not support");
        }
    }

    private static Map<String, BigDecimal> getNewestBinanceKlines(Set<String> cryptocurrencies) {
        Map<String, BigDecimal> klineData = new HashMap<>();
        for (String cryptocurrency : cryptocurrencies) {
            CryptoTypeEnum cryptoTypeEnum = CryptoTypeEnum.valueOf(cryptocurrency);
            klineData.put(cryptocurrency, BinanceTools.getKlines(cryptoTypeEnum, TimeUnitEnum._1M, 1).iterator().next().getClose());
        }
        return klineData;
    }

    private static Map<String, BigDecimal> getNewestOkxKlineData(Set<String> cryptocurrencies) {
        Map<String, BigDecimal> klineData = new HashMap<>();
        for (String cryptocurrency : cryptocurrencies) {
            CryptoTypeEnum cryptoTypeEnum = CryptoTypeEnum.valueOf(cryptocurrency);
            klineData.put(cryptocurrency, OkxApiTools.getKlines(cryptoTypeEnum, TimeUnitEnum._1M, 1).iterator().next().getClose());
        }
        return klineData;
    }

    private static List<OHLCV> modelMapperForEnd(List<KlineData> klines) {
        return klines.stream().filter(KlineData::isEnd)
            .map(kline -> new OHLCV(kline.getOpenTime(), kline.getOpen(), kline.getHigh(), kline.getLow(), kline.getClose(), kline.getVolume())).collect(Collectors.toList());
    }
}
