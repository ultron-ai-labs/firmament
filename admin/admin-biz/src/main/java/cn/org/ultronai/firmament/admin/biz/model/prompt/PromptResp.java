package cn.org.ultronai.firmament.admin.biz.model.prompt;

import lombok.Data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/12 21:28
 */
@Data
public class PromptResp {
    /** 提示词名称 */
    private String name;
    /** 提示词ID */
    private String promptId;
    /** 提示词语种 */
    private String language;
    /** 系统提示词，对系统的说明（告诉系统他是谁） */
    private String systemPrompt;
    /** 交易市场数据提示词：根据语种固定模板 */
    private String marketPrompt;
    /** 交易市场指标提示词：根据语种固定模版，暂时不可修改 */
    private String indicatorPrompt;
    /** 账户提示词：根据语种固定模板 */
    private String accountPrompt;
    /** 账户的订单提示词：根据语种固定模板 */
    private String runningOrderPrompt;
    /** 账户风控提示词 */
    private String riskManagementPrompt;
    /** 交易规则提示词 */
    private String tradingRulePrompt;
    /** 退出策略提示词 */
    private String exitStrategyPrompt;
    /** 交易信号提示词 根据语种固定显示 */
    private String signalPrompt;
    /** 输出格式提示词 根据语种固定显示 */
    private String outputFormatPrompt;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date   createTime;
}
