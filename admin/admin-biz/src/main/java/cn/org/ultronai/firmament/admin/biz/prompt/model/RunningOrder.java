package cn.org.ultronai.firmament.admin.biz.prompt.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * 币种: %s、仓位:%s、杠杆:%s、开仓价:%s、止盈:%s、止损:%s、浮动收益: %s
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/06 20:50
 */
@Data
public class RunningOrder {
    private static final String formatKey = "{%s}";
    /** 币种 */
    private String              coin;
    /** 仓位 */
    private BigDecimal          position;
    /** 杠杆 */
    private BigDecimal          leverage;
    /** 开仓价 */
    private BigDecimal          openPrice;
    /** 预期止盈 */
    private BigDecimal          targetProfitPrice;
    /** 预期止损 */
    private BigDecimal          targetStopLoss;
    /** 做多或做空 */
    private String              positionType;

    public Map<String, String> getRunningOrderMap() {
        Map<String, String> map = new HashMap<>();
        map.put(String.format(formatKey, "coin"), coin);
        map.put(String.format(formatKey, "position"), position.toString());
        map.put(String.format(formatKey, "leverage"), leverage.toString());
        map.put(String.format(formatKey, "openPrice"), openPrice.toString());
        map.put(String.format(formatKey, "targetProfitPrice"), targetProfitPrice.toString());
        map.put(String.format(formatKey, "targetStopLoss"), targetStopLoss.toString());
        map.put(String.format(formatKey, "positionType"), positionType);
        return map;
    }
}
