package cn.org.ultronai.firmament.commonapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/06 21:22
 */
@Getter
@AllArgsConstructor
public enum TimeUnitEnum {
                          /** 1分 */
                          _1M("1m", "1m", "0 */1 * * * *"),
                          /** 3分 */
                          _3M("3m", "3m", "0 */3 * * * *"),
                          /** 5分 */
                          _5M("5m", "5m", "0 */5 * * * *"),
                          /** 15分 */
                          _15M("15m", "15m", "0 */15 * * * *"),
                          /** 30分 */
                          _30M("30m", "30m", "0 */30 * * * *"),
                          /** 1时 */
                          _1H("1H", "1h", "0 0 */1 * * *"),
                          /** 2时 */
                          _2H("2H", "2h", "0 0 */2 * * *"),
                          /** 4时 */
                          _4H("4H", "4h", "0 0 */4 * * *"),
                          /** 6时 */
                          _6H("6H", "6h", "0 0 */6 * * *"),
                          /** 8时 */
                          @Deprecated
                          //                          _8H("8H", "8h"),
                          /** 12时 */
                          _12H("12H", "12h", "0 0 */12 * * *"),
                          /** 1天 */
                          _1D("1D", "1d", "0 0 0 */1 * *"),
    //
    ;

    private final String okx;
    private final String binance;
    private final String cron;

    public String getRealTimeUnit(String exchange) {
        switch (exchange) {
            case "binance":
                return binance;
            case "okx":
                return okx;
            default:
                throw new RuntimeException("exchange not support");
        }
    }

}
