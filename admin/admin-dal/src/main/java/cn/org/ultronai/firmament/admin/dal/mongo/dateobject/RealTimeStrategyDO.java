package cn.org.ultronai.firmament.admin.dal.mongo.dateobject;

import java.math.BigDecimal;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实时数据模型数据对象
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/06 17:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RealTimeStrategyDO extends BaseDO {
    /** 策略名称 */
    private String      strategyName;
    /** 任务策略id */
    private String      strategyId;
    /**
     * 策略状态: init\running\stop
     */
    private String      strategyState;
    /**
     * 策略关联模型ID
     */
    private String      modelId;
    /**
     * 模型分类：对话模型
     */
    private String      modelCategory;
    /**
     * 原始金额
     */
    private BigDecimal  originalAmount;
    /**
     * 当前金额[非滑点]
     */
    private BigDecimal  currentAmount;
    /**
     * 当前金额[带滑点]
     */
    private BigDecimal  currentSlippageAmount = BigDecimal.ZERO;
    /**
     * 订单所有佣金之和
     */
    private BigDecimal  commission            = BigDecimal.ZERO;
    /**
     * 提示词策略id
     */
    private String      promptStrategyId;
    /**
     * 交易所
     */
    private String      exchange;
    /**
     * 加密货币
     */
    private Set<String> cryptocurrencies;
    /**
     * 时间单位
     */
    private String      timeUnit;
    /**
     * 总盈亏比
     */
    private BigDecimal  totalProfitAndLossRatio;
    /**
     * 平均耗时
     */
    private BigDecimal  averageTime;
    /**
     * 开单平仓报警ID
     */
    private String      alertId;
}
