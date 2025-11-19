package cn.org.ultronai.firmament.admin.biz.model.realtimestrategy;

import lombok.Data;

import java.util.Set;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 17:49
 */
@Data
public class RealTimeStrategyAddReq {
    /**
     * 策略名称
     */
    private String      strategyName;
    /**
     * 策略id
     */
    private String      strategyId;
    /**
     * 策略状态
     */
    private String      strategyState;

    /**
     * 模型ID
     */
    private String      modelId;
    /**
     * 模型分类
     */
    private String      modelCategory;
    /**
     * 原始金额
     */
    private Double      originalAmount;
    /**
     * 当前金额
     */
    private Double      currentAmount;
    /**
     * 提示词策略ID
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
     * 开单平仓报警ID
     */
    private String      alertId;
}
