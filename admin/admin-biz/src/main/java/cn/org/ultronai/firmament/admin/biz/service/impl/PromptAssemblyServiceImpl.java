package cn.org.ultronai.firmament.admin.biz.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.org.ultronai.firmament.admin.biz.prompt.model.AccountInfo;
import cn.org.ultronai.firmament.admin.biz.prompt.model.MarketInfo;
import cn.org.ultronai.firmament.admin.biz.prompt.model.RunningOrder;
import cn.org.ultronai.firmament.admin.biz.service.PromptAssemblyService;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.PromptDO;
import cn.org.ultronai.firmament.support.indicator.model.temp.TechnicalIndicators;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/13 08:39
 */
@Service("promptAssemblyService")
public class PromptAssemblyServiceImpl extends BaseService implements PromptAssemblyService {
    /**
     * 获取提示词
     *
     * @param currentMarketInfos 当前市场信息
     * @param account          订单信息
     * @param prompt           prompt
     * @return 提示词
     */
    @Override
    public String getRealPrompt(List<MarketInfo> currentMarketInfos, AccountInfo account, PromptDO prompt) {
        StringBuilder realPrompt = new StringBuilder(StringUtils.EMPTY);
        // 系统提示词 
        realPrompt.append(prompt.getSystemPrompt());
        // 交易市场数据提示词
        for (MarketInfo currentMarketInfo : currentMarketInfos) {
            realPrompt.append(prompt.getMarketPrompt()//
                .replace("{coin}", currentMarketInfo.getCoin())//
                .replace("{timestamp}", currentMarketInfo.getTimestamp())//
                .replace("{open}", currentMarketInfo.getClose().toString())//
                .replace("{high}", currentMarketInfo.getHigh().toString())//
                .replace("{low}", currentMarketInfo.getLow().toString())//
                .replace("{close}", currentMarketInfo.getVol().toString())//
                .replace("{vol}", currentMarketInfo.getVol().toString()));
        }
        // 交易市场指标提示词
        for (MarketInfo currentMarketInfo : currentMarketInfos) {
            TechnicalIndicators indicators = currentMarketInfo.getIndicators();
            realPrompt.append(prompt.getIndicatorPrompt()//
                .replace("{coin}", indicators.getCoin())//
                .replace("{ema9}", String.valueOf(indicators.getEma9()))//
                .replace("{ema20}", String.valueOf(indicators.getEma20()))//
                .replace("{macd}", String.valueOf(indicators.getMacd()))//
                .replace("{macdSignal}", String.valueOf(indicators.getMacdSignal()))//
                .replace("{macdDiff}", String.valueOf(indicators.getMacdDiff()))//
                .replace("{rsi12}", String.valueOf(indicators.getRsi12())//
                    .replace("{rsi24}", String.valueOf(indicators.getRsi24()))));
        }
        // 账户提示词
        realPrompt.append(prompt.getAccountPrompt()//
            .replace("{totalAmount}", account.getTotalAmount().toString())//
            .replace("{totalProfit}", account.getTotalProfit().toString())//
            .replace("{totalOccupiedAmount}", account.getTotalOccupiedAmount().toString())//
            .replace("{longOrderCount}", String.valueOf(account.getLongOrderCount()))//
            .replace("{shortOrderCount}", String.valueOf(account.getShortOrderCount()))//
        );
        // 账户的订单提示词
        if (CollectionUtils.isNotEmpty(account.getRunningOrders())) {
            for (RunningOrder runningOrder : account.getRunningOrders()) {
                realPrompt.append(prompt.getRunningOrderPrompt()//
                    .replace("{coin}", runningOrder.getCoin())//
                    .replace("{position}", runningOrder.getPosition().toString())//
                    .replace("{leverage}", runningOrder.getLeverage().toString())//
                    .replace("{openPrice}", runningOrder.getOpenPrice().toString())//
                    .replace("{targetProfitPrice}", runningOrder.getTargetProfitPrice().toString())//
                    .replace("{targetStopLoss}", runningOrder.getTargetStopLoss().toString())//
                    .replace("{positionType}", runningOrder.getPositionType()));
            }
        }

        // 账户风控提示词
        realPrompt.append(prompt.getRiskManagementPrompt());
        // 交易规则提示词
        realPrompt.append(prompt.getTradingRulePrompt());
        // 退出策略提示词
        realPrompt.append(prompt.getExitStrategyPrompt());
        // 交易信号提示词
        realPrompt.append(prompt.getSignalPrompt());
        // 输出格式提示词
        realPrompt.append(prompt.getOutputFormatPrompt());
        return realPrompt.toString();
    }
}
