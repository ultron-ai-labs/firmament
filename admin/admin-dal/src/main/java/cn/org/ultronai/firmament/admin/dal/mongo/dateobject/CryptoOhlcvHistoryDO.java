package cn.org.ultronai.firmament.admin.dal.mongo.dateobject;

import java.math.BigDecimal;

import lombok.Data;

/**
 * @version Id: CryptoOhlcvHistoryDO, v 2025/11/03 16:23 kai Exp $
 * @author: kai
 * @Description: CryptoOhlcvHistoryDO
 */
@Data
public class CryptoOhlcvHistoryDO extends BaseDO {

    // 币种全局唯一标识格式：交易所-币种名称比如：binance-BTC-USDT-SWAP
    private String cryptoCode;

    // 数据的时间单位比如：1m、3m、5m等
    private String timeUnit;

    // 交易所：binance、okx、gate
    private String exchange;

    // 开盘时间 通常是毫秒级的Unix时间戳
    private Long openTime;

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
    private Long closeTime;

    // 在该时间周期内交易的报价资产的总数量。对于BTC/USDT交易对，这是以USDT计量的总数量。计算方式通常为 成交价 * 基础资产数量 的总和
    private BigDecimal quoteVolume;

    // 在该时间周期内发生的总交易笔数
    private BigDecimal count;

    // 主动买入成交量
    private BigDecimal takerBuyVolume;

    // 主动买入报价资产成交量
    private BigDecimal takerBuyQuoteVolume;

}