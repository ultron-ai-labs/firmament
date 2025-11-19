package cn.org.ultronai.firmament.admin.dal.mongo.dateobject;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * @version Id: OrderDO, v 2025/11/06 19:31 kai Exp $
 * @author: kai
 * @Description: OrderDO
 */
@Data
public class OrderDO extends BaseDO {
    /**
     * RealTimeStrategyDO#strategyId
     */
    private String     strategyId;
    /**
     * 订单流水号
     */
    private String     orderSerialNo;
    /**
     * 币种
     */
    private String     tradeCrypto;
    /**
     * 交易所
     */
    private String     tradeExchange;
    /**
     * long 做多，short 做空
     */
    private String     longOrShort;
    /** 目标止盈 */
    private String     targetTakeProfit;
    /** 订单目标止损 */
    private String     targetStopLoss;
    /**
     * 下单时间
     */
    private Date       bookTime;
    /**
     * 下单价格
     */
    private BigDecimal bookPrice;
    /**
     * 下单滑点价格：请求AI模型之后最新价格
     */
    private BigDecimal bookSlippagePrice;
    /**
     * 下单滑点 = bookSlippagePrice - bookPrice
     */
    private BigDecimal bookSlippage;
    /**
     * 下单倍数
     */
    private Integer    bookMultiple;
    /**
     * 下单佣金
     */
    private BigDecimal bookCommission;
    /**
     * 下单总额
     */
    private BigDecimal bookAmount;
    /**
     * 平仓时间
     */
    private Date       closeTime;
    /**
     * 平仓价格
     */
    private BigDecimal closePrice;
    /**
     * 下单滑点价格：请求AI模型之后最新价格
     */
    private BigDecimal closeSlippagePrice;
    /**
     * 平仓滑点 = closeSlippagePrice - closePrice
     */
    private BigDecimal closeSlippage;
    /**
     * 平仓总额
     */
    private BigDecimal closeAmount;
    /**
     * 平仓佣金
     */
    private BigDecimal closeCommission;
    /**
     * 订单佣金
     */
    private BigDecimal orderCommission;
    /**
     * 总收益额 持有中(需要实时计算金额)
     */
    private BigDecimal totalRevenue;
    /**
     * 总收益率 持有中(需要实时计算金额)
     */
    private BigDecimal totalRate;
    /**
     * 去除佣金收益额
     */
    private BigDecimal realRevenue;
    /**
     * 去除佣金收益率
     */
    private BigDecimal realRate;
    /**
     * 订单状态 0: 持有中 1: 已平仓
     */
    private int        status;
    /**
     * 交易所订单流水号（可选，接入平台可用）
     */
    private String     exchangeOrderNo;
    // ============================== 计算滑点情况下收益额、收益率 ==============================
    /**
     * 订单滑点情况下收益额【不含手续费】
     */
    private BigDecimal slippageTotalRevenue;
    /**
     * 订单滑点情况下收益率【不含手续费】
     */
    private BigDecimal slippageTotalRate;
    /**
     * 订单滑点情况下收益额【含手续费】
     */
    private BigDecimal slippageRealTotalRevenue;
    /**
     * 订单滑点情况下收益率【含手续费】
     */
    private BigDecimal slippageRealTotalRate;
}