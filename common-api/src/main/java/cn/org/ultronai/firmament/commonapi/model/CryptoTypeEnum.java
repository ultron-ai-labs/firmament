package cn.org.ultronai.firmament.commonapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/06 21:20
 */
@Getter
@SuppressWarnings("all")
@AllArgsConstructor
public enum CryptoTypeEnum {
                            /**
                             * BTC
                             */
                            BTC("BTCUSDT", "BTC-USDT-SWAP"),
                            /**
                             * ETH
                             */
                            ETH("ETHUSDT", "ETH-USDT-SWAP"),
                            /**
                             * SOL
                             */
                            SOL("SOLUSDT", "SOL-USDT-SWAP"),
    //
    ;

    private final String binance;
    private final String okx;

    public String getRealCode(String exchange) {
        switch (exchange) {
            case "binance":
                return exchange + "-" + this.binance;
            case "okx":
                return exchange + "-" + this.okx;
            default:
                throw new RuntimeException("不支持的交易所");
        }
    }
}
