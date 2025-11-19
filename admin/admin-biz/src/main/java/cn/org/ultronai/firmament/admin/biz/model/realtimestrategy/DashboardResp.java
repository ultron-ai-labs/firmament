package cn.org.ultronai.firmament.admin.biz.model.realtimestrategy;

import lombok.Data;

/**
 * 总览数据
 * 
 * 总策略佣金(仓位 * 杠杆 / 1000 = 订单佣金)
 * 总策略佣金占比(总策略佣金/初始总金额)
 * 滑点收益差额：滑点收益 - 总收益
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/08 16:52
 */
@Data
public class DashboardResp {
    /**
     * 运行中模型
     */
    private String runningModel;
    /**
     * 初始总金额
     */
    private String totalAmount;
    /**
     * 订单所有佣金之和（不含正在进行的订单）正数
     */
    private String commission;
    /**
     * 订单佣金占比 = commission / totalAmount
     */
    private String commissionRatio;
    /**
     * 剩余总金额(非滑点不去除佣金、不含浮动)
     */
    private String currentTotalAmount;
    /**
     * 总收益（非滑点不去除佣金、不含浮动）
     */
    private String totalProfit;
    /**
     * 总收益率（非滑点不去除佣金、不含浮动）
     */
    private String totalProfitRatio;
    /**
     * 滑点总收益（不去除佣金、不含浮动）
     */
    private String totalSlippageProfit;
    /**
     * 滑点总收益率（不去除佣金、不含浮动）
     */
    private String totalSlippageProfitRatio;
    /**
     * 滑点收益差额 = 滑点总收益 - 总收益
     */
    private String slippageProfitDifference;
    /**
     * 滑点差额收益率 = 滑点收益差额 / 初始总金额
     */
    private String slippageProfitDifferenceRatio;
    /**
     * 接近真实收益额 = 滑点总收益 - 佣金
     */
    private String nearRealProfit;
    /**
     * 接近真实收益率 = (滑点总收益 - 佣金)/初始总金额
     */
    private String nearRealProfitRatio;

    // /**
    //     * 今日收益（不含正在进行的订单）
    //     */
    //    private String todayProfit;
    //    /**
    //     * 今日收益率（不含正在进行的订单）
    //     */
    //    private String todayProfitRatio;
}
