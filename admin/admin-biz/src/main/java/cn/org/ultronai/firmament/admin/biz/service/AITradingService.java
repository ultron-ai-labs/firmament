package cn.org.ultronai.firmament.admin.biz.service;

import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.RealTimeStrategyDO;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 19:00
 */
public interface AITradingService {
    /**
     * AI交易
     *
     * @param realTimeStrategy realTimeStrategy
     */
    void aiTrading(RealTimeStrategyDO realTimeStrategy);
}
