package cn.org.ultronai.firmament.admin.dal.mongo.dateobject;

import java.math.BigDecimal;
import java.util.Set;

import lombok.Data;

/**
 * 回测模型
 *
 * @author icanci
 * @since 1.0 Created in 2025/11/07 15:26
 */
@Data
public class BacktestStrategyDO extends BaseDO {
    /**
     * 模型关联ID
     */
    private String      modelId;
    /**
     * 模型分类：对话模型
     */
    private String      modelCategory;
    /**
     * 回测模型任务id
     */
    private String      backtestStrategyId;
    /**
     * 原始金额
     */
    private BigDecimal  originalAmount;
    /**
     * 当前金额
     */
    private BigDecimal  currentAmount;
    /**
     * 系统提示词
     */
    private String      systemPrompt;
    /**
     * 用户提示词 【带有模板的】
     */
    private String      userPrompt;
    /**
     * 加密货币
     */
    private Set<String> cryptocurrencies;
    /**
     * 时间单位
     */
    private String      timeUnit;
    /**
     * 整体进度: k线总量
     */
    private long        overallProgress;
    /**
     * 当前进度: k线进度
     */
    private long        currentProgress;
    /**
     * 平均耗时
     */
    private BigDecimal  averageTime;
    /**
     * 策略状态: 未开始、进行中、暂停、已完成
     */
    private String      strategyState;
    // 当前策略的归档数据，比如胜率、盈亏比、等等
}
