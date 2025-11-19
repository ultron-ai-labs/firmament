package cn.org.ultronai.firmament.support.indicator.expansion.volatility;

import java.util.ArrayList;
import java.util.List;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.AbstractIndicator;
import org.ta4j.core.num.Num;

/**
 * 自定义波动率指标：计算最近 N 根K线的收益率标准差，用于判断震荡
 * 若标准差在 [0.05 ~ 0.10]（即 5%~10%），则认为是震荡
 *
 * @author icanci
 * @since 1.0 Created in 2025/11/16 10:05
 */
public class VolatilityIndicator extends AbstractIndicator<Num> {

    private final int       windowSize;          // 统计最近 N 根K线
    private final BarSeries series;
    private final Num       zero    = numOf(0);
    // 如果你用百分比，可以不用，这里用小数
    private final Num       hundred = numOf(100);

    public VolatilityIndicator(BarSeries series, int windowSize) {
        super(series);
        this.series = series;
        // 比如 20 根K线
        this.windowSize = windowSize;
    }

    @Override
    public Num getValue(int index) {
        if (index < windowSize - 1) {
            return numOf(Double.NaN); // 不足窗口，返回无效值
        }

        List<Num> returns = new ArrayList<>(windowSize);

        // 取最近 N 根K线，计算收益率: (当前收盘 - 前一根收盘) / 前一根收盘
        for (int i = index - windowSize + 1; i <= index; i++) {
            Bar currBar = series.getBar(i);
            Bar prevBar = series.getBar(i - 1);

            Num currClose = currBar.getClosePrice();
            Num prevClose = prevBar.getClosePrice();

            if (prevClose.isZero()) {
                return numOf(Double.NaN);
            }

            // 收益率 = (当前收盘 - 前收盘) / 前收盘
            Num ret = currClose.minus(prevClose).dividedBy(prevClose);
            returns.add(ret);
        }

        // 计算收益率的：均值 & 标准差
        Num mean = calculateMean(returns);
        Num variance = calculateVariance(returns, mean);
        Num stdDev = variance.sqrt(); // 标准差

        // 如果你想要的是 “百分比波动率”，比如 0.05 => 5%，可以乘以 100
        // 但判断时依旧用小数：if (stdDev.isGreaterThanOrEqual(numOf(0.05)) && stdDev.isLessThanOrEqual(numOf(0.10)))

        return stdDev;
    }

    private Num calculateMean(List<Num> values) {
        Num sum = numOf(0);
        for (Num v : values) {
            sum = sum.plus(v);
        }
        return sum.dividedBy(numOf(values.size()));
    }

    private Num calculateVariance(List<Num> values, Num mean) {
        Num variance = numOf(0);
        for (Num v : values) {
            Num diff = v.minus(mean);
            variance = variance.plus(diff.multipliedBy(diff));
        }
        return variance.dividedBy(numOf(values.size()));
    }

}
