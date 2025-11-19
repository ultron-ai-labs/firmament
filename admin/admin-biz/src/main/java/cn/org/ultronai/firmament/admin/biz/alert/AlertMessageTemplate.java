package cn.org.ultronai.firmament.admin.biz.alert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringJoiner;

import cn.org.ultronai.firmament.admin.biz.model.realtimestrategy.WarningResult;
import cn.org.ultronai.firmament.admin.biz.prompt.model.MarketInfo;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.OrderDO;
import cn.org.ultronai.firmament.commonapi.model.TimeUnitEnum;

/**
 * 报警信息模板
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/13 11:14
 */
//@SuppressWarnings("all")
public class AlertMessageTemplate {

    /**
     * 未持仓
     */
    public static String toHoldString(String strategyName, TimeUnitEnum timeUnit, String coin, String currentPrice, WarningResult result) {
        return new StringJoiner("\n")//
            .add("AI分析报警 " + timeUnit.name() + "\n")//
            .add("策略名称: " + strategyName)//
            .add("币种: " + coin)//
            .add("时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))//
            .add("方向: " + result.gSignal())//
            .add("当前价格(收盘价): " + currentPrice)//
            .toString();
    }

    /**
     * 持仓
     */
    public static String toHoldFloatString(String strategyName, TimeUnitEnum timeUnit, String longOrShort, String coinFloat, //
                                           String coinFloatRote, String currentPrice, OrderDO order) {
        return new StringJoiner("\n")//
            .add("AI分析报警 " + timeUnit.name() + "\n")//
            .add("策略名称: " + strategyName)//
            .add("币种: " + order.getTradeCrypto())//
            .add("时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))//
            .add("方向: " + longOrShort)//
            .add("下单价格:" + order.getBookPrice())//
            .add("下单总额:" + order.getBookAmount())//
            .add("杠杆:" + order.getBookMultiple())//
            .add("当前价格(收盘价): " + currentPrice)//
            .add("浮动收益:" + String.format("%s(%s)", coinFloat, coinFloatRote))//
            .toString();
    }

    /**
     * 下单
     */
    public static String toBookString(String strategyName, TimeUnitEnum timeUnit, MarketInfo marketInfo, WarningResult result) {
        return new StringJoiner("\n")//
            .add("AI分析报警 " + timeUnit.name() + "\n")//
            .add("策略名称: " + strategyName)//
            .add("币种: " + marketInfo.getCoin())//
            .add("时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))//
            .add("方向: " + result.gSignal())//
            .add("当前价格(收盘价): " + marketInfo.getClose().toPlainString())//
            .add("目标止盈: " + result.getProfit_target())//
            .add("目标止损: " + result.getStop_loss())//
            .add("信心: " + result.getConfidence())//
            .add("杠杆: " + result.getLeverage())//
            .add("盈亏比: " + gProfitAndLossRatio(result, marketInfo)).//
            add("下单总额: " + result.getQuantity())//
            .toString();
    }

    /**
     * 平仓
     */
    public static String toCloseString(String strategyName, TimeUnitEnum timeUnit, //
                                       String profit, String profitRote, MarketInfo marketInfo, WarningResult result) {
        return new StringJoiner("\n")//
            .add("AI分析报警 " + timeUnit.name() + "\n")//
            .add("策略名称: " + strategyName)//
            .add("币种: " + marketInfo.getCoin())//
            .add("时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))//
            .add("方向: " + result.gSignal())//
            .add("当前价格(收盘价): " + marketInfo.getClose().toPlainString())//
            .add("平仓盈亏: " + profit + "(" + profitRote + "%)")//
            .toString();
    }

    /**
     * 平仓
     */
    public static String toCloseOrderString(String strategyName, TimeUnitEnum timeUnit, //
                                            String profit, String profitRote, String coin, BigDecimal newestPrice, String gSignal) {
        return new StringJoiner("\n")//
            .add("AI分析报警 " + timeUnit.name() + "\n")//
            .add("策略名称: " + strategyName)//
            .add("币种: " + coin)//
            .add("时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))//
            .add("方向: " + gSignal)//
            .add("当前价格(收盘价): " + newestPrice.toPlainString())//
            .add("平仓盈亏(手动平仓): " + profit + "(" + profitRote + "%)")//
            .toString();
    }

    /**
     * 盈亏比
     *
     * @param result result
     * @param marketInfo marketInfo
     * @return 盈亏比
     */
    private static String gProfitAndLossRatio(WarningResult result, MarketInfo marketInfo) {
        BigDecimal bdProfitTarget = new BigDecimal(result.getProfit_target());
        BigDecimal bdCurrentPrice = marketInfo.getClose();
        BigDecimal bdStopLoss = new BigDecimal(result.getStop_loss());
        BigDecimal molecule = bdProfitTarget.subtract(bdCurrentPrice).abs();
        BigDecimal denominator = bdStopLoss.subtract(bdCurrentPrice).abs();
        return molecule.divide(denominator, 2, RoundingMode.HALF_UP).abs().setScale(2, RoundingMode.HALF_UP).toPlainString();
    }
}
