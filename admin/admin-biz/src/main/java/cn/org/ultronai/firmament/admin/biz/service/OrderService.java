/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.org.ultronai.firmament.admin.biz.service;

import cn.org.ultronai.firmament.admin.biz.model.order.CloseOrderReq;
import cn.org.ultronai.firmament.admin.biz.model.order.OrderPageResp;
import cn.org.ultronai.firmament.admin.biz.model.order.OrderReq;
import cn.org.ultronai.firmament.admin.biz.model.realtimestrategy.WarningResult;
import cn.org.ultronai.firmament.admin.biz.prompt.model.AccountInfo;
import cn.org.ultronai.firmament.admin.biz.prompt.model.MarketInfo;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.RealTimeStrategyDO;

/**
 * 订单服务接口
 *
 * @author kai
 * @since 1.0 Created in 2025/11/06 20:15
 */
public interface OrderService {

    /**
     * 查询订单信息
     *
     * @param realTimeStrategy realTimeStrategy
     * @return 订单信息
     */
    AccountInfo queryOrderInfo(RealTimeStrategyDO realTimeStrategy);

    /**
     * 持有订单
     *
     * @param realTimeStrategy realTimeStrategy
     * @param cryptocurrency   cryptocurrency
     * @param currMarketInfo   currMarketInfo
     * @param result           result
     */
    void hold(RealTimeStrategyDO realTimeStrategy, String cryptocurrency, MarketInfo currMarketInfo, WarningResult result);

    /**
     * 卖出
     *
     * @param realTimeStrategy realTimeStrategy
     * @param cryptocurrency   cryptocurrency
     * @param currMarketInfo   currMarketInfo
     * @param result           result
     */
    void sellToEnter(RealTimeStrategyDO realTimeStrategy, String cryptocurrency, MarketInfo currMarketInfo, WarningResult result);

    /**
     * 买入
     *
     * @param realTimeStrategy realTimeStrategy
     * @param cryptocurrency   cryptocurrency
     * @param currMarketInfo   currMarketInfo
     * @param result           result
     */
    void buyToEnter(RealTimeStrategyDO realTimeStrategy, String cryptocurrency, MarketInfo currMarketInfo, WarningResult result);

    /**
     * 平仓
     *
     * @param realTimeStrategy realTimeStrategy
     * @param cryptocurrency   cryptocurrency
     * @param currMarketInfo   currMarketInfo
     * @param result           result
     */
    void closePosition(RealTimeStrategyDO realTimeStrategy, String cryptocurrency, MarketInfo currMarketInfo, WarningResult result);

    /**
     * 平空仓
     *
     * @param realTimeStrategy realTimeStrategy
     * @param cryptocurrency   cryptocurrency
     * @param currMarketInfo   currMarketInfo
     * @param result           result
     */
    void closeShortSellingOrders(RealTimeStrategyDO realTimeStrategy, String cryptocurrency, MarketInfo currMarketInfo, WarningResult result);

    /**
     * 平多仓
     *
     * @param realTimeStrategy realTimeStrategy
     * @param cryptocurrency   cryptocurrency
     * @param currMarketInfo   currMarketInfo
     * @param result           result
     */
    void closeLongOrders(RealTimeStrategyDO realTimeStrategy, String cryptocurrency, MarketInfo currMarketInfo, WarningResult result);

    /**
     * 策略订单查询
     *
     * @param accessToken accessToken
     * @param req         req
     * @return 策略订单查询
     */
    OrderPageResp strategyOrders(String accessToken, OrderReq req);

    /**
     * 策略订单手动平仓
     *
     * @param accessToken accessToken
     * @param req         req
     * @return 策略订单手动平仓
     */
    boolean closeOrder(String accessToken, CloseOrderReq req);
}