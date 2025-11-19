package cn.org.ultronai.firmament.support.indicator.local;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

import cn.org.ultronai.firmament.support.indicator.model.OHLCV;

/**
 * local indicator api tool base
 *
 * @author icanci
 * @since 1.0 Created in 2025/11/15 17:23
 */
@SuppressWarnings("all")
public abstract class BaseIndicatorApiTool {
    /**
     * 对K线进行排序，因为指标的要求需要有序K线
     *
     * @return List<Bar>
     */
    protected static List<OHLCV> sortedKlineData(List<OHLCV> klineData) {
        klineData.sort(Comparator.comparingLong(OHLCV::getOpenTime));
        return klineData;
    }

    /**
     * 对K线进行排序，因为指标的要求需要有序K线
     *
     * @return List<Bar>
     */
    protected static List<Bar> sortedBarData(List<OHLCV> klineData) {
        klineData = sortedKlineData(klineData);
        // 按时间顺序添加K线数据（OKX返回的数据是倒序的）
        BarSeries series = new BaseBarSeries("sorted_data");
        for (OHLCV kline : klineData) {
            ZonedDateTime dateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(kline.getOpenTime()), ZoneId.systemDefault());
            double open = kline.getOpen();
            double high = kline.getHigh();
            double low = kline.getLow();
            double close = kline.getClose();
            double volume = kline.getVolume();
            series.addBar(dateTime, open, high, low, close, volume);
        }
        return series.getBarData();
    }

    /**
    * 对K线BarSeries
    *  
    * @return BarSeries
    */
    protected static BarSeries buildBarSeries(List<OHLCV> klineData) {
        klineData = sortedKlineData(klineData);
        // 按时间顺序添加K线数据（OKX返回的数据是倒序的）
        BarSeries series = new BaseBarSeries("sorted_data");
        for (OHLCV kline : klineData) {
            ZonedDateTime dateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(kline.getOpenTime()), ZoneId.systemDefault());
            double open = kline.getOpen();
            double high = kline.getHigh();
            double low = kline.getLow();
            double close = kline.getClose();
            double volume = kline.getVolume();
            series.addBar(dateTime, open, high, low, close, volume);
        }
        return series;
    }
}
