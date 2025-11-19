package cn.org.ultronai.firmament.support.indicator;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

import cn.org.ultronai.firmament.support.indicator.local.LocalIndicatorApiTools;
import cn.org.ultronai.firmament.support.indicator.model.OHLCV;
import cn.org.ultronai.firmament.support.indicator.model.temp.TechnicalIndicators;
import cn.org.ultronai.firmament.support.indicator.value.MacdIndicatorValue;

/**
 * @author icanci
 * @since 1.0 Created in 2025/10/29 15:43
 */
public class TempIndicatorApiTools {
    /**
     * 计算技术指标 - 修正版本
     */
    public static TechnicalIndicators calculateIndicators(String coin, List<OHLCV> klineData) {
        if (klineData == null || klineData.size() < 26) {
            throw new IllegalArgumentException("K线数据不足，至少需要26条数据");
        }

        // 创建时间序列
        BarSeries series = new BaseBarSeries("okx_data");

        klineData.sort((o1, o2) -> Long.compare(o2.getOpenTime(), o1.getOpenTime()));

        // 按时间顺序添加K线数据（OKX返回的数据是倒序的）
        for (int i = klineData.size() - 1; i >= 0; i--) {
            OHLCV kline = klineData.get(i);

            ZonedDateTime dateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(kline.getOpenTime()), ZoneId.systemDefault());

            double open = kline.getOpen();
            double high = kline.getHigh();
            double low = kline.getLow();
            double close = kline.getClose();
            double volume = kline.getVolume();

            series.addBar(dateTime, open, high, low, close, volume);
        }
        // 计算技术指标
        TechnicalIndicators technicalIndicators = new TechnicalIndicators();
        technicalIndicators.setCoin(coin);
        technicalIndicators.setEma9(LocalIndicatorApiTools.calculateEMA(series, 9));
        technicalIndicators.setEma20(LocalIndicatorApiTools.calculateEMA(series, 20));
        MacdIndicatorValue macdIndicatorValue = LocalIndicatorApiTools.calculateMACD(series, 12, 26, 9);
        technicalIndicators.setMacd(macdIndicatorValue.getMacd());
        technicalIndicators.setMacdSignal(macdIndicatorValue.getMacdSignal());
        technicalIndicators.setRsi12(LocalIndicatorApiTools.calculateRSI(series, 12));
        technicalIndicators.setRsi24(LocalIndicatorApiTools.calculateRSI(series, 24));
        return technicalIndicators;
    }

}
