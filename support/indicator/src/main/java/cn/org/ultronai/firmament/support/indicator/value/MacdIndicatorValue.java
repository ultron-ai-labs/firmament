package cn.org.ultronai.firmament.support.indicator.value;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * macd指标值
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/15 17:54
 */
@Data
@AllArgsConstructor
public class MacdIndicatorValue {
    private double shortEma;
    private double longEma;
    private double macd;
    private double macdSignal;
}
