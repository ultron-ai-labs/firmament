package cn.org.ultronai.firmament.admin.biz.prompt.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import cn.org.ultronai.firmament.support.indicator.model.temp.TechnicalIndicators;
import lombok.Data;

/**
 * 币种: {0}、开盘价：{1}、收盘价：{2}、最高价：{3}、最低价：{4}、成交量：{5}、EMA4：{6}、EMA12：{7}、过去20根k线成交量：{8}
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/06 20:48
 */
@Data
public class MarketInfo {
    private static final String formatKey = "{%s}";
    private String              timestamp;
    private String              coin;
    private BigDecimal          open;
    private BigDecimal          close;
    private BigDecimal          high;
    private BigDecimal          low;
    private BigDecimal          vol;
    private TechnicalIndicators indicators;
    // 最新价格
    private BigDecimal          newestPrice;

    public Map<String, String> getMarketMap() {
        Map<String, String> map = new HashMap<>();
        map.put(String.format(formatKey, "coin"), coin);
        map.put(String.format(formatKey, "open"), open.toPlainString());
        map.put(String.format(formatKey, "close"), close.toPlainString());
        map.put(String.format(formatKey, "high"), high.toPlainString());
        map.put(String.format(formatKey, "low"), low.toPlainString());
        map.put(String.format(formatKey, "vol"), vol.toPlainString());
        return map;
    }
}
