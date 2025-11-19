package cn.org.ultronai.firmament.support.indicator.model.temp;

import org.ta4j.core.indicators.*;
import org.ta4j.core.indicators.adx.ADXIndicator;
import org.ta4j.core.indicators.adx.MinusDIIndicator;
import org.ta4j.core.indicators.adx.PlusDIIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsLowerIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsMiddleIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsUpperIndicator;
import org.ta4j.core.indicators.helpers.*;
import org.ta4j.core.indicators.ichimoku.IchimokuKijunSenIndicator;
import org.ta4j.core.indicators.ichimoku.IchimokuSenkouSpanAIndicator;
import org.ta4j.core.indicators.ichimoku.IchimokuSenkouSpanBIndicator;
import org.ta4j.core.indicators.ichimoku.IchimokuTenkanSenIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.indicators.volume.NVIIndicator;
import org.ta4j.core.indicators.volume.OnBalanceVolumeIndicator;
import org.ta4j.core.indicators.volume.PVIIndicator;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TODO 识别指标
 *
 * @author icanci
 * @since 1.0 Created in 2025/11/12 14:02
 */
@Getter
@AllArgsConstructor
public enum Ta4jIndicatorEnum {
                               // ======== Trend 类 ========
                               /** 简单移动平均 */
                               SMA("SMAIndicator", "trend", "SMA", "简单移动平均", SMAIndicator.class),
                               /** 指数移动平均 */
                               EMA("EMAIndicator", "trend", "EMA", "指数移动平均", EMAIndicator.class),
                               /** 加权移动平均 */
                               WMA("WMAIndicator", "trend", "WMA", "加权移动平均", WMAIndicator.class),
                               /** 指数平滑异同平均线 */
                               MACD("MACDIndicator", "trend", "MACD", "指数平滑异同平均线", MACDIndicator.class),
                               /** 商品通道指数 */
                               CCI("CCIIndicator", "trend", "CCI", "商品通道指数", CCIIndicator.class),
                               /** 去趋势价格振荡器 */
                               DPO("DPOIndicator", "trend", "DPO", "去趋势价格振荡器", DPOIndicator.class),
                               /** 考夫曼自适应均线 */
                               KAMA("KAMAIndicator", "trend", "KAMA", "考夫曼自适应均线", KAMAIndicator.class),
                               /** 百分比价格振荡器 */
                               PPO("PPOIndicator", "trend", "PPO", "百分比价格振荡器", PPOIndicator.class),
                               /** Hull移动平均 */
                               HMA("HMAIndicator", "trend", "HMA", "Hull移动平均", HMAIndicator.class),
                               /** 线性加权移动平均 */
                               LWMA("LWMAIndicator", "trend", "LWMA", "线性加权移动平均", LWMAIndicator.class),
                               /** 一目均衡基准线 */
                               IchimokuKijunSen("IchimokuKijunSenIndicator", "trend", "IchimokuKijunSen", "一目均衡基准线", IchimokuKijunSenIndicator.class),
                               /** 一目均衡转换线 */
                               IchimokuTenkanSen("IchimokuTenkanSenIndicator", "trend", "IchimokuTenkanSen", "一目均衡转换线", IchimokuTenkanSenIndicator.class),
                               /** 一目均衡先行A */
                               IchimokuSenkouSpanA("IchimokuSenkouSpanAIndicator", "trend", "IchimokuSenkouSpanA", "一目均衡先行A", IchimokuSenkouSpanAIndicator.class),
                               /** 一目均衡先行B */
                               IchimokuSenkouSpanB("IchimokuSenkouSpanBIndicator", "trend", "IchimokuSenkouSpanB", "一目均衡先行B", IchimokuSenkouSpanBIndicator.class),

                               // ======== Momentum 类 ========

                               /** 相对强弱指数 */
                               RSI("RSIIndicator", "momentum", "RSI", "相对强弱指数", RSIIndicator.class),
                               /** 随机指标K */
                               StochasticK("StochasticOscillatorKIndicator", "momentum", "StochasticK", "随机指标K", StochasticOscillatorKIndicator.class),
                               /** 随机指标D */
                               StochasticD("StochasticOscillatorDIndicator", "momentum", "StochasticD", "随机指标D", StochasticOscillatorDIndicator.class),
                               /** 威廉指标 %R */
                               WilliamsR("WilliamsRIndicator", "momentum", "WilliamsR", "威廉指标 %R", WilliamsRIndicator.class),
                               /** 变化率 */
                               ROC("ROCIndicator", "momentum", "ROC", "变化率", ROCIndicator.class),
                               /** 震荡指标 */
                               AO("AwesomeOscillatorIndicator", "momentum", "AO", "震荡指标", AwesomeOscillatorIndicator.class),

                               // ======== Volatility 类 ========

                               /** 平均真实波幅 */
                               ATR("ATRIndicator", "volatility", "ATR", "平均真实波幅", ATRIndicator.class),
                               /** 布林带中轨 */
                               BBMiddle("BollingerBandsMiddleIndicator", "volatility", "BBMiddle", "布林带中轨", BollingerBandsMiddleIndicator.class),
                               /** 布林带上轨 */
                               BBUpper("BollingerBandsUpperIndicator", "volatility", "BBUpper", "布林带上轨", BollingerBandsUpperIndicator.class),
                               /** 布林带下轨 */
                               BBLower("BollingerBandsLowerIndicator", "volatility", "BBLower", "布林带下轨", BollingerBandsLowerIndicator.class),
                               /** 标准差 */
                               StdDev("StandardDeviationIndicator", "volatility", "StdDev", "标准差", StandardDeviationIndicator.class),

                               // ======== Volume 类 ========

                               /** 能量潮 */
                               OBV("OnBalanceVolumeIndicator", "volume", "OBV", "能量潮", OnBalanceVolumeIndicator.class),
                               /** 负成交量指数 */
                               NVI("NVIIndicator", "volume", "NVI", "负成交量指数", NVIIndicator.class),
                               /** 正成交量指数 */
                               PVI("PVIIndicator", "volume", "PVI", "正成交量指数", PVIIndicator.class),

                               // ======== Price 类 ========

                               /** 收盘价 */
                               ClosePrice("ClosePriceIndicator", "price", "ClosePrice", "收盘价", ClosePriceIndicator.class),
                               /** 开盘价 */
                               OpenPrice("OpenPriceIndicator", "price", "OpenPrice", "开盘价", OpenPriceIndicator.class),
                               /** 最高价 */
                               HighPrice("HighPriceIndicator", "price", "HighPrice", "最高价", HighPriceIndicator.class),
                               /** 最低价 */
                               LowPrice("LowPriceIndicator", "price", "LowPrice", "最低价", LowPriceIndicator.class),
                               /** 典型价格 */
                               TypicalPrice("TypicalPriceIndicator", "price", "TypicalPrice", "典型价格", TypicalPriceIndicator.class),
                               /** 中位价格 */
                               MedianPrice("MedianPriceIndicator", "price", "MedianPrice", "中位价格", MedianPriceIndicator.class),

                               // ======== Other 类 ========
                               /** 抛物线转向指标 */
                               ParabolicSAR("ParabolicSarIndicator", "other", "ParabolicSAR", "抛物线转向指标", ParabolicSarIndicator.class),
                               /** 平均趋向指数 */
                               ADX("ADXIndicator", "other", "ADX", "平均趋向指数", ADXIndicator.class),
                               /** 负趋向指标 */
                               MinusDI("MinusDIIndicator", "other", "MinusDI", "负趋向指标", MinusDIIndicator.class),
                               /** 正趋向指标 */
                               PlusDI("PlusDIIndicator", "other", "PlusDI", "正趋向指标", PlusDIIndicator.class);

    ;

    // 指标名称
    private final String name;
    // 分类大类: trend、volume
    private final String category;
    // 子类，对应指标库的指标
    private final String subCategory;
    // 描述
    private final String fullDescription;
    // 指标类
    private final Class<?> classType;
}
