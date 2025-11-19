package cn.org.ultronai.firmament.exchange.binance;

import java.util.List;

import cn.hutool.http.HttpUtil;
import cn.org.ultronai.firmament.commonapi.model.CryptoTypeEnum;
import cn.org.ultronai.firmament.commonapi.model.KlineData;
import cn.org.ultronai.firmament.commonapi.model.TimeUnitEnum;

/**
 * binance 工具类
 * https://developers.binance.com/docs/zh-CN/binance-spot-api-docs/rest-api/general-api-information
 *
 * https://api.binance.com
 * https://api-gcp.binance.com
 * https://api1.binance.com
 * https://api2.binance.com
 * https://api3.binance.com
 * https://api4.binance.com
 * @author icanci
 * @since 1.0 Created in 2025/11/06 09:16
 */
public class BinanceTools {
    private static final String binance_api_url = "https://api.binance.com";
    private static final String klines          = "/api/v3/klines";

    /**
     * https://api.binance.com/api/v3/klines?symbol=BTCUSDT&interval=5m&limit=500
     * 
     * @param cryptoType  cryptoType
     * @param timeUnit timeUnit
     * @param limit limit
     * @return  url path
     */
    private static String getKlinesUrl(CryptoTypeEnum cryptoType, TimeUnitEnum timeUnit, int limit) {
        return binance_api_url + klines + "?symbol=" + cryptoType.getBinance() + "&interval=" + timeUnit.getBinance() + "&limit=" + limit;
    }

    /**
     * 数据格式
     *
     * 1499040000000,      // 开盘时间
     * "0.01634790",       // 开盘价
     * "0.80000000",       // 最高价
     * "0.01575800",       // 最低价
     * "0.01577100",       // 收盘价(当前K线未结束的即为最新价)
     * "148976.11427815",  // 成交量
     * 1499644799999,      // 收盘时间
     * "2434.19055334",    // 成交额
     * 308,                // 成交笔数
     * "1756.87402397",    // 主动买入成交量
     * "28.46694368",      // 主动买入成交额
     * "17928899.62484339" // 请忽略该参数
     * [
     * [
     * 1762392300000,
     * "102888.02000000",
     * "102934.82000000",
     * "102775.08000000",
     * "102775.09000000",
     * "151.45124000",
     * 1762392599999,
     * "15576815.23635080",
     * 13516,
     * "27.39557000",
     * "2817809.73576390",
     * "0"
     * ]
     * ]
     * @param cryptoType cryptoType
     * @param timeUnit timeUnit
     * @return List<CryptoOhlcvHistoryDO>
     */
    public static List<KlineData> getKlines(CryptoTypeEnum cryptoType, TimeUnitEnum timeUnit, int limit) {
        String responseBody = HttpUtil.get(getKlinesUrl(cryptoType, timeUnit, limit));
        return BinanceResponseParser.parseKlines(cryptoType, timeUnit, responseBody);
    }
}
