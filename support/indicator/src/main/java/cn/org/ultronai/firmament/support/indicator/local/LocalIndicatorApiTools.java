package cn.org.ultronai.firmament.support.indicator.local;

import java.util.List;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.*;
import org.ta4j.core.indicators.adx.ADXIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsLowerIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsMiddleIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsUpperIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.num.Num;

import cn.org.ultronai.firmament.support.indicator.model.OHLCV;
import cn.org.ultronai.firmament.support.indicator.value.BollingerBandIndicatorValue;
import cn.org.ultronai.firmament.support.indicator.value.MacdIndicatorValue;

/**
 * 本地计算指标工作器类
 * 用于计算各种独立指标
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/15 17:22
 */
public class LocalIndicatorApiTools extends BaseIndicatorApiTool {
    /**
     * 计算EMA
     *
     * @param klineData klineData
     * @param period period
     * @return double
     */
    public static double calculateEMA(List<OHLCV> klineData, int period) {
        if (klineData.size() < period) {
            throw new RuntimeException("[calculateEMA] K线数据长度不足");
        }
        BarSeries series = buildBarSeries(klineData);
        return calculateEMA(series, period);
    }

    /**
     * 计算EMA
     *
     * @param series series
     * @param period period
     * @return double
     */
    public static double calculateEMA(BarSeries series, int period) {
        // 基础指标
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        EMAIndicator emaIndicator = new EMAIndicator(closePrice, period);
        return emaIndicator.getValue(series.getEndIndex()).doubleValue();
    }

    /**
     * 计算SMA
     *
     * @param klineData klineData
     * @param period period
     * @return double
     */
    public static double calculateSMA(List<OHLCV> klineData, int period) {
        if (klineData.size() < period) {
            throw new RuntimeException("[calculateSMA]K线数据长度不足");
        }
        BarSeries series = buildBarSeries(klineData);
        return calculateSMA(series, period);
    }

    /**
     * 计算SMA
     *
     * @param series series
     * @param period period
     * @return double
     */
    public static double calculateSMA(BarSeries series, int period) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        SMAIndicator smaIndicator = new SMAIndicator(closePrice, period);
        return smaIndicator.getValue(series.getEndIndex()).doubleValue();
    }

    /**
     * 计算MACD
     * 
     * @param klineData klineData
     * @param shortPeriod shortPeriod
     * @param longPeriod longPeriod
     * @param signalPeriod signalPeriod
     * @return MacdIndicatorValue
     */
    public static MacdIndicatorValue calculateMACD(List<OHLCV> klineData, int shortPeriod, int longPeriod, int signalPeriod) {
        if (klineData.size() < longPeriod) {
            throw new RuntimeException("[calculateMACD]K线数据长度不足");
        }
        BarSeries series = buildBarSeries(klineData);
        return calculateMACD(series, shortPeriod, longPeriod, signalPeriod);
    }

    /**
     * 计算MACD
     *
     * @param series series
     * @param shortPeriod shortPeriod
     * @param longPeriod longPeriod
     * @param signalPeriod signalPeriod
     * @return MacdIndicatorValue
     */
    public static MacdIndicatorValue calculateMACD(BarSeries series, int shortPeriod, int longPeriod, int signalPeriod) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        // MACD
        MACDIndicator macdIndicator = new MACDIndicator(closePrice, shortPeriod, longPeriod);
        // MACD signal line (通常为9)
        EMAIndicator macdSignal = new EMAIndicator(macdIndicator, signalPeriod);
        double shortEma = macdIndicator.getShortTermEma().getValue(series.getEndIndex()).doubleValue();
        double longEma = macdIndicator.getLongTermEma().getValue(series.getEndIndex()).doubleValue();
        double macdValue = macdIndicator.getValue(series.getEndIndex()).doubleValue();
        double macdSignalValue = macdSignal.getValue(series.getEndIndex()).doubleValue();
        return new MacdIndicatorValue(shortEma, longEma, macdValue, macdSignalValue);
    }

    /**
     * 计算RSI
     * 
     * @param klineData klineData
     * @param period  period
     * @return  double
     */
    public static double calculateRSI(List<OHLCV> klineData, int period) {
        if (klineData.size() < period) {
            throw new RuntimeException("[calculateRSI]K线数据长度不足");
        }
        BarSeries series = buildBarSeries(klineData);
        return calculateRSI(series, period);
    }

    /**
     * 计算RSI
     *
     * @param series series
     * @param period  period
     * @return  double
     */
    public static double calculateRSI(BarSeries series, int period) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        RSIIndicator rsiIndicator = new RSIIndicator(closePrice, period);
        return rsiIndicator.getValue(series.getEndIndex()).doubleValue();
    }

    /**
     * 计算ATR
     *
     * @param klineData klineData
     * @param period period
     * @return  double
     */
    public static double calculateATR(List<OHLCV> klineData, int period) {
        if (klineData.size() < period) {
            throw new RuntimeException("[calculateATR]K线数据长度不足");
        }
        BarSeries series = buildBarSeries(klineData);
        return calculateATR(series, period);
    }

    /**
     * 计算ATR
     *
     * @param series series
     * @param period period
     * @return  double
     */
    public static double calculateATR(BarSeries series, int period) {
        ATRIndicator atr = new ATRIndicator(series, period);
        return atr.getValue(series.getEndIndex()).doubleValue();
    }

    /**
     * 计算 ADX
     *
     * @param klineData klineData
     * @param period period
     * @return  double
     */
    public static double calculateADX(List<OHLCV> klineData, int period) {
        if (klineData.size() < period) {
            throw new RuntimeException("[calculateADX]K线数据长度不足");
        }
        BarSeries series = buildBarSeries(klineData);
        return calculateADX(series, period);
    }

    /**
     * 计算 ADX
     *
     * @param series series
     * @param period period
     * @return  double
     */
    public static double calculateADX(BarSeries series, int period) {
        ADXIndicator adx = new ADXIndicator(series, period);
        return adx.getValue(series.getEndIndex()).doubleValue();
    }

    /**
     * 计算BollingerBands
     *  
     * @param klineData klineData
     * @param bollPeriod bollPeriod
     * @param deviation  deviation
     * @return BollingerBandIndicatorValue
     */
    public static BollingerBandIndicatorValue calculateBollingerBands(List<OHLCV> klineData, int bollPeriod, int deviation) {
        if (klineData.size() < bollPeriod) {
            throw new RuntimeException("[calculateBollingerBands]K线数据长度不足");
        }
        BarSeries series = buildBarSeries(klineData);
        return calculateBollingerBands(series, bollPeriod, deviation);
    }

    /**
     * 计算BollingerBands
     *
     * @param series series
     * @param bollPeriod bollPeriod
     * @param deviation  deviation
     * @return BollingerBandIndicatorValue
     */
    public static BollingerBandIndicatorValue calculateBollingerBands(BarSeries series, int bollPeriod, int deviation) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        BollingerBandsMiddleIndicator middleBand = new BollingerBandsMiddleIndicator(closePrice);

        // 中轨：SMA(close, 20)
        BollingerBandsMiddleIndicator middle = new BollingerBandsMiddleIndicator(new ClosePriceIndicator(series));

        // 标准差：std(close, 20)
        StandardDeviationIndicator stdDev = new StandardDeviationIndicator(new ClosePriceIndicator(series), bollPeriod);

        // 倍数 k
        Num k = series.numOf(deviation);

        // 上轨：middle + k * stdDev
        Indicator<Num> upper = new BollingerBandsUpperIndicator(middle, stdDev, k);

        // 下轨：middle - k * stdDev
        Indicator<Num> lower = new BollingerBandsLowerIndicator(middle, stdDev, k);

        Num middleBandValue = middleBand.getValue(series.getEndIndex());
        Num upperBandValue = upper.getValue(series.getEndIndex());
        Num lowerBandValue = lower.getValue(series.getEndIndex());
        return new BollingerBandIndicatorValue(middleBandValue.doubleValue(), upperBandValue.doubleValue(), lowerBandValue.doubleValue());
    }
}
