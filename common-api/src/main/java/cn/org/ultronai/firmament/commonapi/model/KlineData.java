package cn.org.ultronai.firmament.commonapi.model;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 统一的k线数据：实时k线数据计算
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/06 21:19
 */
@Data
public class KlineData {
    // 币种全局唯一标识格式：交易所-币种名称比如：binance-BTC-USDT-SWAP
    private String     cryptoCode;

    // 数据的时间单位比如：1m、3m、5m等
    private String     timeUnit;

    // 交易所：binance、okx、gate
    private String     exchange;

    // 开盘时间 通常是毫秒级的Unix时间戳
    private Long       openTime;

    // 开盘价 在该时间周期内第一笔成交交易的价格
    private BigDecimal open;

    // 最高价 在该时间周期内所有成交交易中的最高价格
    private BigDecimal high;

    // 最低价 在该时间周期内所有成交交易中的最低价格
    private BigDecimal low;

    // 收盘价 在该时间周期内最后一笔成交交易的价格
    private BigDecimal close;

    // 成交量 在该时间周期内交易的基础资产的总数量。例如，对于BTC/USDT交易对，它是以BTC计量的总数量。
    private BigDecimal volume;

    // 收盘时间 通常是毫秒级的Unix时间戳
    private Long       closeTime;
    /**
     * 是否是k线结束
     */
    private boolean    end;
}
