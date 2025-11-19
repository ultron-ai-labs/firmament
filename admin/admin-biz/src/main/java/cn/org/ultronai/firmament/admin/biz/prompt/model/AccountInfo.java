package cn.org.ultronai.firmament.admin.biz.prompt.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * 你的账户信息：总账户金额：{0}$，亏损金额：{1}$，可用余额：{2}$,进行中的订单
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/06 20:48
 */
@Data
public class AccountInfo {
    private static final String formatKey = "{%s}";
    // 总账户金额
    private BigDecimal          totalAmount;
    // 已实现盈亏金额
    private BigDecimal          totalProfit;
    // 占用在金额中的金额
    private BigDecimal          totalOccupiedAmount;
    private int                 longOrderCount;
    private int                 shortOrderCount;

    private List<RunningOrder>  runningOrders;

    public Map<String, String> getAccountInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put(String.format(formatKey, "totalAmount"), totalAmount.toString());
        map.put(String.format(formatKey, "totalProfit"), totalProfit.toString());
        map.put(String.format(formatKey, "totalOccupiedAmount"), totalOccupiedAmount.toString());
        map.put(String.format(formatKey, "longOrderCount"), String.valueOf(longOrderCount));
        map.put(String.format(formatKey, "shortOrderCount"), String.valueOf(shortOrderCount));
        return map;
    }
}
