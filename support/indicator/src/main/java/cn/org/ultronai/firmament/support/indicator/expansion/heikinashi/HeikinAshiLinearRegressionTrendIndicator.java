package cn.org.ultronai.firmament.support.indicator.expansion.heikinashi;

import java.util.ArrayList;
import java.util.List;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.AbstractIndicator;
import org.ta4j.core.num.Num;

/**
 * 平均K线趋势：线性回归算法
 * 值绝对值越大，说明当前方向的趋势越强（>1 多头，<1 空头，0 震荡）
 * 多头输出：> 1.0 且 ≤ 10.0 多头趋势，数值越大趋势越强
 * 空头输出：< -1 且 ≥ -10.0 空头趋势，数值越小趋势越强
 * 震荡市场：震荡市场，无明显方向
 * @author icanci
 * @since 1.0 Created in 2025/11/15 18:02
 */
public class HeikinAshiLinearRegressionTrendIndicator extends AbstractIndicator<Num> {
    /**
     * 观察的K线根数，比如 10 或 14 或者其他数据
     * （如 1h 图用 lookback=14，15m 图用 lookback=8）
     */
    private final int    lookBackPeriod;

    /**
     * 🆕 新增：震荡判断的阈值，upCount 和 downCount 相差不超过此值，则认为是震荡
     * 0.001→ 非常小的趋势才算有方向
     * 0.01~ 0.05→ 更实用，推荐用于一般 K线
     * 0.1→ 较大的趋势才被承认
     */
    private final double trendDifferenceThreshold;

    public HeikinAshiLinearRegressionTrendIndicator(BarSeries series, int lookBackPeriod, double trendDifferenceThreshold) {
        super(HeikinAshiConverter.convertToHeikinAshi(series));
        if (lookBackPeriod <= 0) {
            throw new IllegalArgumentException("lookbackPeriod 必须 > 0");
        }
        if (trendDifferenceThreshold < 0) {
            throw new IllegalArgumentException("trendDifferenceThreshold 必须 >= 0");
        }

        this.lookBackPeriod = lookBackPeriod;
        this.trendDifferenceThreshold = trendDifferenceThreshold;
    }

    /**
     * @param index the bar index
     * @return the value of the indicator
     */
    @Override
    public Num getValue(int index) {
        if (index < lookBackPeriod - 1) {
            return numOf(0);
        }

        // 取最近 N 根 K线
        List<Bar> window = new ArrayList<>();
        for (int i = index - lookBackPeriod + 1; i <= index; i++) {
            window.add(getBarSeries().getBar(i));
        }

        int n = window.size();
        double xSum = 0.0, ySum = 0.0, xySum = 0.0, xxSum = 0.0;

        for (int i = 0; i < n; i++) {
            double x = i; // 0, 1, 2, ..., N-1
            double y = window.get(i).getClosePrice().doubleValue();

            xSum += x;
            ySum += y;
            xySum += x * y;
            xxSum += x * x;
        }

        // 一元线性回归公式：slope (b) = (n*xySum - xSum*ySum) / (n*xxSum - xSum*xSum)
        double slopeNumerator = n * xySum - xSum * ySum;
        double slopeDenominator = n * xxSum - xSum * xSum;

        if (Math.abs(slopeDenominator) < 1e-10) {
            // 分母接近 0，无法计算斜率，认为是震荡
            return numOf(0);
        }

        double slope = slopeNumerator / slopeDenominator;

        // =========================
        // 震荡判断：如果斜率绝对值 < threshold，返回 0
        if (Math.abs(slope) < trendDifferenceThreshold) {
            return numOf(0);
        }
        // =========================

        // 斜率正负代表方向，绝对值代表强度
        boolean isUptrend = slope > 0;
        double absSlope = Math.abs(slope);

        // 将斜率强度映射到 [0.1 ~ 1.0]，然后映射到 [1~10] 或 [-1~-10]
        double strength = Math.min(Math.max(absSlope / 0.01, 0.1), 1.0); // 可调整分母以控制灵敏度
        strength = Math.min(Math.max(strength, 0.1), 1.0);

        if (isUptrend) {
            double trendValue = 1.0 + (strength * 9.0); // [1.0 ~ 10.0]
            return numOf(trendValue);
        } else {
            double trendValue = -1.0 - (strength * 9.0); // [-1.0 ~ -10.0]
            return numOf(trendValue);
        }
    }
}
