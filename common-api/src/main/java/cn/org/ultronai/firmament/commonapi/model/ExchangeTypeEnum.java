package cn.org.ultronai.firmament.commonapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 交易所类型
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/04 20:59
 */
@Getter
@AllArgsConstructor
public enum ExchangeTypeEnum {
                              /**
                               * 币安
                               */
                              BINANCE("binance"),
                              /**
                               * OKX
                               */
                              OKX("okx"),

    //
    ;

    private final String type;
}
