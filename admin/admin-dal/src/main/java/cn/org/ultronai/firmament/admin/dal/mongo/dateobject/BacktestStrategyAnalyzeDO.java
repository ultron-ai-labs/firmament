package cn.org.ultronai.firmament.admin.dal.mongo.dateobject;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 回测模型分析数据对象
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/07 15:42
 */
@Data
public class BacktestStrategyAnalyzeDO extends BaseDO {
    /**
     * 回测模型任务id
     */
    private String     backtestStrategyId;
    /**
     * 回测订单总量
     */
    private long       orderCount;
    /**
     * 回测订单总金额
     */
    private BigDecimal orderAmount;
    /**
     * 回测订单总手续费
     */
    private BigDecimal orderCommission;
    /**
     * 回测订单总盈亏
     */
    private BigDecimal orderProfit;
    /**
     * 回测订单总盈亏率
     */
    private BigDecimal orderProfitRate;
    /**
     * 多单数量
     */
    private long       longCount;
    /**
     * 空单数量
     */
    private long       shortCount;
    /**
     * 多单占比
     */
    private BigDecimal longRate;
    /**
     * 空单占比
     */
    private BigDecimal shortRate;
    /**
     * 夏普比率
     */
    private BigDecimal sharpRatio;
    /**
     * 模型分析结果描述: 请求ai 模型分析结果
     */
    private String     analyzeResultDesc;
}
