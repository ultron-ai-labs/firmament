package cn.org.ultronai.firmament.support.indicator.value;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/17 10:52
 */
@Data
@AllArgsConstructor
public class BollingerBandIndicatorValue {
    private double middleBandValue;
    private double upperBandValue;
    private double lowerBandValue;
}
