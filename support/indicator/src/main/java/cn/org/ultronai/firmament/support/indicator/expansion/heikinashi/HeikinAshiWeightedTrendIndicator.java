package cn.org.ultronai.firmament.support.indicator.expansion.heikinashi;

import java.util.ArrayList;
import java.util.List;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.AbstractIndicator;
import org.ta4j.core.num.Num;

/**
 * 平均K线趋势：加权算法
 * 值绝对值越大，说明当前方向的趋势越强（>1 多头，<1 空头，0 震荡）
 * 多头输出：> 1.0 且 ≤ 10.0 多头趋势，数值越大趋势越强
 * 空头输出：< -1 且 ≥ -10.0 空头趋势，数值越小趋势越强
 * 震荡市场：震荡市场，无明显方向
 * @author icanci
 * @since 1.0 Created in 2025/11/15 18:02
 */
public class HeikinAshiWeightedTrendIndicator extends AbstractIndicator<Num> {
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

    public HeikinAshiWeightedTrendIndicator(BarSeries series, int lookBackPeriod, double trendDifferenceThreshold) {
        super(series);
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

        List<Bar> window = new ArrayList<>();
        for (int i = index - lookBackPeriod + 1; i <= index; i++) {
            window.add(getBarSeries().getBar(i));
        }

        double weightedSum = 0.0;
        double totalWeight = 0.0;

        for (int i = 0; i < window.size() - 1; i++) {
            Bar prevBar = window.get(i);
            Bar currBar = window.get(i + 1);

            double prevClose = prevBar.getClosePrice().doubleValue();
            double currClose = currBar.getClosePrice().doubleValue();

            double diff = currClose - prevClose;

            int weight = (window.size() - 1) - i; // 最新 bar 权重最高
            weightedSum += diff * weight;
            totalWeight += weight;
        }

        if (totalWeight == 0) {
            return numOf(0);
        }

        double weightedTrend = weightedSum / totalWeight;

        // ======================
        // ✅ 恢复并正确使用 trendDifferenceThreshold
        // 如果加权趋势绝对值太小，认为是震荡，返回 0
        if (Math.abs(weightedTrend) < trendDifferenceThreshold) {
            return numOf(0);
        }
        // ======================

        boolean isUptrend = weightedTrend > 0;
        double absTrend = Math.abs(weightedTrend);

        // 将趋势强度映射到 [0.1 ~ 1.0]，然后转为 [1~10] 或 [-1~-10]
        double strength = Math.min(Math.max(absTrend / 0.1, 0.1), 1.0); // 可调整分母
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
