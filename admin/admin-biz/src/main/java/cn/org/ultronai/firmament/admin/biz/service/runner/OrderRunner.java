package cn.org.ultronai.firmament.admin.biz.service.runner;

import java.util.concurrent.Callable;

import cn.org.ultronai.firmament.admin.biz.model.realtimestrategy.WarningResult;
import cn.org.ultronai.firmament.admin.biz.prompt.model.MarketInfo;
import cn.org.ultronai.firmament.admin.biz.service.OrderService;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.RealTimeStrategyDO;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/13 12:20
 */
public class OrderRunner implements Callable<Object> {
    private final OrderService       orderService;
    private final WarningResult      result;
    private final MarketInfo         currMarketInfo;
    private final RealTimeStrategyDO realTimeStrategy;
    private final String             cryptocurrency;

    public OrderRunner(OrderService orderService, WarningResult result, MarketInfo currMarketInfo, RealTimeStrategyDO realTimeStrategy, String cryptocurrency) {
        this.orderService = orderService;
        this.result = result;
        this.currMarketInfo = currMarketInfo;
        this.realTimeStrategy = realTimeStrategy;
        this.cryptocurrency = cryptocurrency;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Object call() throws Exception {

        // 5.对订单数据下单，和可用余额总额进行扣减
        String signal = result.getSignal();
        // 做空
        if ("sell_to_enter".equals(signal)) {
            orderService.sellToEnter(realTimeStrategy, cryptocurrency, currMarketInfo, result);
        }
        // 做多
        if ("buy_to_enter".equals(signal)) {
            orderService.buyToEnter(realTimeStrategy, cryptocurrency, currMarketInfo, result);
        }
        // 平仓
        if ("close_position".equals(signal)) {
            orderService.closePosition(realTimeStrategy, cryptocurrency, currMarketInfo, result);
        }
        // 持有
        // 计算当前订单的浮动盈亏
        if ("hold".equals(signal)) {
            orderService.hold(realTimeStrategy, cryptocurrency, currMarketInfo, result);
        }
        // 平空
        if ("close_short_orders".equals(signal)) {
            orderService.closeShortSellingOrders(realTimeStrategy, cryptocurrency, currMarketInfo, result);
        }
        // 平多
        if ("close_long_orders".equals(signal)) {
            orderService.closeLongOrders(realTimeStrategy, cryptocurrency, currMarketInfo, result);
        }
        return new Object();
    }
}
