package cn.org.ultronai.firmament.support.indicator.model.temp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/10/29 15:28
 */
@Data
public class TechnicalIndicators implements Serializable {
    private static final String formatKey = "{%s}";
    private String              coin;
    private double              ema9;
    private double              ema20;
    private double              macd;
    private double              macdSignal;
    private double              macdDiff;
    private double              rsi12;
    private double              rsi24;

    public Map<String, String> getIndicatorMap() {
        Map<String, String> map = new HashMap<>();
        map.put(String.format(formatKey, "coin"), coin);
        map.put(String.format(formatKey, "ema9"), String.valueOf(ema9));
        map.put(String.format(formatKey, "ema20"), String.valueOf(ema20));
        map.put(String.format(formatKey, "macd"), String.valueOf(macd));
        map.put(String.format(formatKey, "macdSignal"), String.valueOf(macdSignal));
        map.put(String.format(formatKey, "macdDiff"), String.valueOf(macdDiff));
        map.put(String.format(formatKey, "rsi12"), String.valueOf(rsi12));
        map.put(String.format(formatKey, "rsi24"), String.valueOf(rsi24));
        return map;
    }

}
