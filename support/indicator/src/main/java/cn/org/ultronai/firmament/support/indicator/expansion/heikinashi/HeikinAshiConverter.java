package cn.org.ultronai.firmament.support.indicator.expansion.heikinashi;

import java.time.ZonedDateTime;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.num.DecimalNum;
import org.ta4j.core.num.Num;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/15 18:04
 */
public class HeikinAshiConverter {

    /**
     * 将普通的 BarSeries（传统K线）转换为 Heikin-Ashi BarSeries
     *
     * @param series 原始 K线数据（基于 Ta4j BarSeries）
     * @return Heikin-Ashi 格式的 BarSeries
     */
    public static BarSeries convertToHeikinAshi(BarSeries series) {
        Num prevHaOpen = null;
        Num prevHaClose = null;

        BarSeries heikinAshiSeries = new BaseBarSeries("Heikin-Ashi Series");

        for (int i = 0; i < series.getBarCount(); i++) {
            Bar originalBar = series.getBar(i);
            Num open = originalBar.getOpenPrice();
            Num high = originalBar.getHighPrice();
            Num low = originalBar.getLowPrice();
            Num close = originalBar.getClosePrice();
            Num volume = originalBar.getVolume(); // 可保留原始成交量
            ZonedDateTime endTime = originalBar.getEndTime();

            // ===== 1. HA-Close = (Open + High + Low + Close) / 4 =====
            Num haClose = open.plus(high).plus(low).plus(close).dividedBy(numOf(4));

            // ===== 2. HA-Open = (上一根HA-Open + 上一根HA-Close) / 2 =====
            Num haOpen;
            if (prevHaOpen == null || prevHaClose == null) {
                // 第1根：简化处理，取 (Open + Close) / 2 或直接 Open
                haOpen = open.plus(close).dividedBy(numOf(2));
            } else {
                haOpen = prevHaOpen.plus(prevHaClose).dividedBy(numOf(2));
            }

            // ===== 3. HA-High = MAX(High, HA-Open, HA-Close) =====
            Num haHigh = high.max(haOpen).max(haClose);

            // ===== 4. HA-Low = MIN(Low, HA-Open, HA-Close) =====
            Num haLow = low.min(haOpen).min(haClose);

            // ===== 5. 构建 HA Bar =====

            heikinAshiSeries.addBar(endTime, haOpen, haHigh, haLow, haClose, volume);

            // 保存当前 HA-Open 和 HA-Close，用于下一根计算
            prevHaOpen = haOpen;
            prevHaClose = haClose;
        }

        // 构建新的 Heikin-Ashi BarSeries
        return heikinAshiSeries;
    }

    // 辅助方法：创建 Num 对象（兼容 Ta4j 内部 Num 类型，如 DoubleNum）
    private static Num numOf(Number value) {
        return DecimalNum.valueOf(value);
    }
}
