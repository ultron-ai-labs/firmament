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
package cn.org.ultronai.firmament.admin.biz.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.hutool.json.JSONUtil;
import cn.org.ultronai.firmament.admin.biz.alert.AlertMessageTemplate;
import cn.org.ultronai.firmament.admin.biz.mapper.OrderMapper;
import cn.org.ultronai.firmament.admin.biz.model.order.CloseOrderReq;
import cn.org.ultronai.firmament.admin.biz.model.order.OrderPageResp;
import cn.org.ultronai.firmament.admin.biz.model.order.OrderReq;
import cn.org.ultronai.firmament.admin.biz.model.realtimestrategy.WarningResult;
import cn.org.ultronai.firmament.admin.biz.prompt.model.AccountInfo;
import cn.org.ultronai.firmament.admin.biz.prompt.model.MarketInfo;
import cn.org.ultronai.firmament.admin.biz.prompt.model.RunningOrder;
import cn.org.ultronai.firmament.admin.biz.service.AlertService;
import cn.org.ultronai.firmament.admin.biz.service.LogService;
import cn.org.ultronai.firmament.admin.biz.service.MarketService;
import cn.org.ultronai.firmament.admin.biz.service.OrderService;
import cn.org.ultronai.firmament.admin.biz.utils.PrefixUtils;
import cn.org.ultronai.firmament.admin.dal.common.PageList;
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.OrderDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.RealTimeStrategyDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.OrderDO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.RealTimeStrategyDO;
import cn.org.ultronai.firmament.commonapi.model.TimeUnitEnum;

/**
 * 订单服务实现类
 *
 * @author kai
 * @since 1.0 Created in 2025/11/06 20:20
 */
@Service
public class OrderServiceImpl extends BaseService implements OrderService {
    @Resource
    private AlertService        alertService;
    @Resource
    private LogService          logService;
    @Resource
    private OrderDAO            orderDAO;
    @Resource
    private RealTimeStrategyDAO realTimeStrategyDAO;
    @Resource
    private MarketService       marketService;
    @Resource
    private OrderMapper         orderMapper;

    /**
     * 查询订单信息
     *
     * @param realTimeStrategy realTimeStrategy
     * @return 订单信息
     */
    @Override
    public AccountInfo queryOrderInfo(RealTimeStrategyDO realTimeStrategy) {
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setTotalAmount(realTimeStrategy.getCurrentAmount());

        String strategyId = realTimeStrategy.getStrategyId();
        List<OrderDO> order = orderDAO.queryByStrategyId(strategyId);
        int longOrders = (int) order.stream().filter(orderDO -> StringUtils.equals("long", orderDO.getLongOrShort())).count();
        accountInfo.setLongOrderCount(longOrders);
        accountInfo.setShortOrderCount(order.size() - longOrders);
        //  0: 持有中 1: 已平仓
        List<OrderDO> endOrder = order.stream().filter(orderDO -> orderDO.getStatus() == 1).collect(Collectors.toList());
        accountInfo.setTotalProfit(endOrder.stream().map(OrderDO::getTotalRevenue).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));
        // 持有中
        List<OrderDO> holdOrder = order.stream().filter(orderDO -> orderDO.getStatus() == 0).collect(Collectors.toList());
        accountInfo.setTotalOccupiedAmount(holdOrder.stream().map(OrderDO::getBookAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));
        accountInfo.setRunningOrders(holdOrder.stream().map(orderDO -> {
            RunningOrder runningOrder = new RunningOrder();
            runningOrder.setCoin(orderDO.getTradeCrypto());
            runningOrder.setPosition(orderDO.getBookAmount());
            runningOrder.setLeverage(new BigDecimal(orderDO.getBookMultiple()));
            runningOrder.setOpenPrice(orderDO.getBookPrice());
            runningOrder.setTargetProfitPrice(new BigDecimal(orderDO.getTargetTakeProfit()));
            runningOrder.setTargetStopLoss(new BigDecimal(orderDO.getTargetStopLoss()));
            runningOrder.setPositionType(orderDO.getLongOrShort());
            return runningOrder;
        }).collect(Collectors.toList()));
        return accountInfo;
    }

    /**
     * 持有订单
     *
     * @param realTimeStrategy realTimeStrategy
     * @param cryptocurrency   cryptocurrency
     * @param currMarketInfo   currMarketInfo
     * @param result           result
     */
    @Override
    public void hold(RealTimeStrategyDO realTimeStrategy, String cryptocurrency, MarketInfo currMarketInfo, WarningResult result) {
        // 查询订单
        OrderDO runningOrder = findRunningOrder(realTimeStrategy.getStrategyId(), cryptocurrency);
        String strategyName = realTimeStrategy.getStrategyName();
        TimeUnitEnum timeUnit = TimeUnitEnum.valueOf(realTimeStrategy.getTimeUnit());

        if (runningOrder == null) {
            // 发送订单消息
            String message = AlertMessageTemplate.toHoldString(strategyName, timeUnit, cryptocurrency, currMarketInfo.getClose().toPlainString(), result);
            alertService.doAlert(realTimeStrategy.getAlertId(), realTimeStrategy.getMemberId(), message);
        } else {
            // 计算浮动盈亏和收益率
            BigDecimal totalRevenue = buildTotalRevenue(runningOrder, currMarketInfo.getClose());
            BigDecimal totalRate = buildTotalRate(runningOrder, totalRevenue);
            // totalRate * 100 保留2位小数
            totalRate = totalRate.multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
            // totalRevenue 保留2位小数
            totalRevenue = totalRevenue.setScale(2, RoundingMode.HALF_UP);
            String ls = runningOrder.getLongOrShort().equals("long") ? "多" : "空";

            String message = AlertMessageTemplate.toHoldFloatString(strategyName, timeUnit, ls, totalRevenue.toPlainString(), totalRate.toString() + "%",
                currMarketInfo.getClose().toPlainString(), runningOrder);
            alertService.doAlert(realTimeStrategy.getAlertId(), realTimeStrategy.getMemberId(), message);
        }
    }

    /**
     * 卖出
     *
     * @param realTimeStrategy realTimeStrategy
     * @param cryptocurrency   cryptocurrency
     * @param currMarketInfo   currMarketInfo
     * @param result           result
     */
    @Override
    public void sellToEnter(RealTimeStrategyDO realTimeStrategy, String cryptocurrency, MarketInfo currMarketInfo, WarningResult result) {
        String signal = result.gSignal();
        OrderDO order = bookOrder(realTimeStrategy, cryptocurrency, currMarketInfo, result, "short");
        logService.logOrder(order.getOrderSerialNo(), order.getMemberId(), signal);
        // 入场通知
        String message = AlertMessageTemplate.toBookString(realTimeStrategy.getStrategyName(), TimeUnitEnum.valueOf(realTimeStrategy.getTimeUnit()), currMarketInfo, result);
        alertService.doAlert(realTimeStrategy.getAlertId(), realTimeStrategy.getMemberId(), message);
    }

    /**
     * 买入
     *
     * @param realTimeStrategy realTimeStrategy
     * @param cryptocurrency   cryptocurrency
     * @param currMarketInfo   currMarketInfo
     * @param result           result
     */
    @Override
    public void buyToEnter(RealTimeStrategyDO realTimeStrategy, String cryptocurrency, MarketInfo currMarketInfo, WarningResult result) {
        String signal = result.gSignal();
        OrderDO order = bookOrder(realTimeStrategy, cryptocurrency, currMarketInfo, result, "long");
        logService.logOrder(order.getOrderSerialNo(), order.getMemberId(), signal);
        // 入场通知
        String message = AlertMessageTemplate.toBookString(realTimeStrategy.getStrategyName(), TimeUnitEnum.valueOf(realTimeStrategy.getTimeUnit()), currMarketInfo, result);
        alertService.doAlert(realTimeStrategy.getAlertId(), realTimeStrategy.getMemberId(), message);
    }

    /**
     * 平仓
     *
     * @param realTimeStrategy realTimeStrategy
     * @param cryptocurrency   cryptocurrency
     * @param currMarketInfo   currMarketInfo
     * @param result           result
     */
    @Override
    public void closePosition(RealTimeStrategyDO realTimeStrategy, String cryptocurrency, MarketInfo currMarketInfo, WarningResult result) {
        OrderDO runningOrder = findRunningOrder(realTimeStrategy.getStrategyId(), cryptocurrency);
        if (runningOrder == null) {
            return;
        }
        String signal = result.gSignal();
        logService.logOrder(runningOrder.getOrderSerialNo(), runningOrder.getMemberId(), signal);
        closeOrder(runningOrder, realTimeStrategy, currMarketInfo.getClose(), currMarketInfo.getNewestPrice());
        // 保留两位小数
        BigDecimal totalRevenue = runningOrder.getTotalRevenue().setScale(2, RoundingMode.HALF_UP);
        // 乘以100保留2位小数
        BigDecimal totalRate = runningOrder.getTotalRate().multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
        String message = AlertMessageTemplate.toCloseString(realTimeStrategy.getStrategyName(), TimeUnitEnum.valueOf(realTimeStrategy.getTimeUnit()), totalRevenue.toPlainString(),
            totalRate.toPlainString(), currMarketInfo, result);
        alertService.doAlert(realTimeStrategy.getAlertId(), realTimeStrategy.getMemberId(), message);
    }

    /**
     * 平空仓
     *
     * @param realTimeStrategy realTimeStrategy
     * @param cryptocurrency   cryptocurrency
     * @param currMarketInfo   currMarketInfo
     * @param result           result
     */
    @Override
    public void closeShortSellingOrders(RealTimeStrategyDO realTimeStrategy, String cryptocurrency, MarketInfo currMarketInfo, WarningResult result) {
        OrderDO runningOrder = findRunningOrder(realTimeStrategy.getStrategyId(), cryptocurrency);
        if (runningOrder == null) {
            return;
        }
        String signal = result.gSignal();
        logService.logOrder(runningOrder.getOrderSerialNo(), runningOrder.getMemberId(), signal);
        closeOrder(runningOrder, realTimeStrategy, currMarketInfo.getClose(), currMarketInfo.getNewestPrice());
        // 保留两位小数
        BigDecimal totalRevenue = runningOrder.getTotalRevenue().setScale(2, RoundingMode.HALF_UP);
        // 乘以100保留2位小数
        BigDecimal totalRate = runningOrder.getTotalRate().multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
        String message = AlertMessageTemplate.toCloseString(realTimeStrategy.getStrategyName(), TimeUnitEnum.valueOf(realTimeStrategy.getTimeUnit()), totalRevenue.toPlainString(),
            totalRate.toPlainString(), currMarketInfo, result);
        alertService.doAlert(realTimeStrategy.getAlertId(), realTimeStrategy.getMemberId(), message);
    }

    /**
     * 平多仓
     *
     * @param realTimeStrategy realTimeStrategy
     * @param cryptocurrency   cryptocurrency
     * @param currMarketInfo   currMarketInfo
     * @param result           result
     */
    @Override
    public void closeLongOrders(RealTimeStrategyDO realTimeStrategy, String cryptocurrency, MarketInfo currMarketInfo, WarningResult result) {
        OrderDO runningOrder = findRunningOrder(realTimeStrategy.getStrategyId(), cryptocurrency);
        if (runningOrder == null) {
            return;
        }
        String signal = result.gSignal();
        logService.logOrder(runningOrder.getOrderSerialNo(), runningOrder.getMemberId(), signal);
        closeOrder(runningOrder, realTimeStrategy, currMarketInfo.getClose(), currMarketInfo.getNewestPrice());
        // 保留两位小数
        BigDecimal totalRevenue = runningOrder.getTotalRevenue().setScale(2, RoundingMode.HALF_UP);
        // 乘以100保留2位小数
        BigDecimal totalRate = runningOrder.getTotalRate().multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
        String message = AlertMessageTemplate.toCloseString(realTimeStrategy.getStrategyName(), TimeUnitEnum.valueOf(realTimeStrategy.getTimeUnit()), totalRevenue.toPlainString(),
            totalRate.toPlainString(), currMarketInfo, result);
        alertService.doAlert(realTimeStrategy.getAlertId(), realTimeStrategy.getMemberId(), message);
    }

    /**
     * 策略订单查询
     *
     * @param accessToken accessToken
     * @param req         req
     * @return 策略订单查询
     */
    @Override
    public OrderPageResp strategyOrders(String accessToken, OrderReq req) {
        getUserDO(accessToken);
        RealTimeStrategyDO realTimeStrategy = realTimeStrategyDAO.queryByStrategyId(req.getStrategyId());
        if (realTimeStrategy == null) {
            throw new RuntimeException("策略不存在");
        }

        PageList<OrderDO> orders = orderDAO.queryPageByStrategyId(req.getStrategyId(), req.getTradeCrypto(), req.getLongOrShort(), req.getCloseStatus(), req.getPage(),
            req.getPageSize());
        Collection<OrderDO> data = orders.getData();
        // 查询实时数据修复数据用来展示浮动盈亏
        fixOpenInterestOrderData(realTimeStrategy, data);
        OrderPageResp orderPageResp = new OrderPageResp();
        orderPageResp.setTotal(orders.getPaginator().getTotalCount());
        orderPageResp.setPage(req.getPage());
        orderPageResp.setPageSize(req.getPageSize());
        orderPageResp.setRecords(data.stream().filter(new Predicate<OrderDO>() {
            @Override
            public boolean test(OrderDO orderDO) {
                if (StringUtils.isBlank(req.getProfitStatus())) {
                    return true;
                }
                if (StringUtils.equals(req.getProfitStatus(), "profit")) {
                    return orderDO.getTotalRevenue().compareTo(new BigDecimal("0")) > 0;
                }

                if (StringUtils.equals(req.getProfitStatus(), "loss")) {
                    return orderDO.getTotalRevenue().compareTo(new BigDecimal("0")) <= 0;
                }

                return true;
            }
        }).map(item -> orderMapper.toDTO(item)).collect(Collectors.toList()));
        return orderPageResp;
    }

    /**
     * 策略订单手动平仓
     *
     * @param accessToken accessToken
     * @param req         req
     * @return 策略订单手动平仓
     */
    @Override
    public boolean closeOrder(String accessToken, CloseOrderReq req) {
        getUserDO(accessToken);
        RealTimeStrategyDO realTimeStrategy = realTimeStrategyDAO.queryByStrategyId(req.getStrategyId());
        if (realTimeStrategy == null) {
            throw new RuntimeException("策略不存在");
        }
        OrderDO runningOrder = orderDAO.queryByOrderId(req.getOrderSerialNo());
        if (runningOrder == null) {
            throw new RuntimeException("订单不存在");
        }
        if (runningOrder.getStatus() == 1) {
            return true;
        }
        // 查询最新价
        BigDecimal currentPrice = marketService.queryNewestMarketInfo(realTimeStrategy.getExchange(), runningOrder.getTradeCrypto());
        if (currentPrice == null) {
            throw new RuntimeException("最新价格数据不存在");
        }
        // 关闭订单
        closeOrder(runningOrder, realTimeStrategy, currentPrice, currentPrice);

        // 发送平仓消息
        // 保留两位小数
        BigDecimal totalRevenue = runningOrder.getTotalRevenue().setScale(2, RoundingMode.HALF_UP);
        // 乘以100保留2位小数
        BigDecimal totalRate = runningOrder.getTotalRate().multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
        String gSignal = runningOrder.getLongOrShort().equals("long") ? "手动平多仓" : "手动平空仓";

        String message = AlertMessageTemplate.toCloseOrderString(realTimeStrategy.getStrategyName(), TimeUnitEnum.valueOf(realTimeStrategy.getTimeUnit()),
            totalRevenue.toPlainString(), totalRate.toPlainString(), runningOrder.getTradeCrypto(), currentPrice, gSignal);

        alertService.doAlert(realTimeStrategy.getAlertId(), realTimeStrategy.getMemberId(), message);
        return true;
    }

    /**
     * 修复持仓数据
     *
     * @param realTimeStrategy realTimeStrategy
     * @param data             data
     */
    private void fixOpenInterestOrderData(RealTimeStrategyDO realTimeStrategy, Collection<OrderDO> data) {
        String exchange = realTimeStrategy.getExchange();
        Set<String> cryptocurrencies = realTimeStrategy.getCryptocurrencies();
        Map<String, BigDecimal> newestPriceMap = marketService.queryNewestMarketInfo(exchange, cryptocurrencies);
        for (OrderDO orderDO : data) {
            if (orderDO.getStatus() == 0) {
                orderDO.setCloseTime(new Date());
                BigDecimal totalRevenue = buildTotalRevenue(orderDO, newestPriceMap.get(orderDO.getTradeCrypto()));
                orderDO.setTotalRevenue(totalRevenue);
                orderDO.setTotalRate(buildTotalRate(orderDO, totalRevenue));
            }
        }
    }

    private BigDecimal buildTotalRevenue(OrderDO runningOrder, BigDecimal close) {
        // long
        // totalRevenue= ((close-book)/book) * bookAmount * bookMultiple
        //  short
        // totalRevenue= ((book-close)/book) * bookAmount
        if ("long".equals(runningOrder.getLongOrShort())) {
            return runningOrder.getBookAmount().multiply(new BigDecimal(runningOrder.getBookMultiple()))
                .multiply(close.subtract(runningOrder.getBookPrice()).divide(runningOrder.getBookPrice(), 8, RoundingMode.HALF_UP));
        }
        if ("short".equals(runningOrder.getLongOrShort())) {
            return runningOrder.getBookAmount().multiply(new BigDecimal(runningOrder.getBookMultiple()))
                .multiply(runningOrder.getBookPrice().subtract(close).divide(runningOrder.getBookPrice(), 8, RoundingMode.HALF_UP));
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal buildTotalRevenueForSlippage(OrderDO runningOrder, BigDecimal close) {
        // long
        // totalRevenue= ((close-book)/book) * bookAmount * bookMultiple
        //  short
        // totalRevenue= ((book-close)/book) * bookAmount
        if ("long".equals(runningOrder.getLongOrShort())) {
            return runningOrder.getBookAmount().multiply(new BigDecimal(runningOrder.getBookMultiple()))
                .multiply(close.subtract(runningOrder.getBookSlippagePrice()).divide(runningOrder.getBookSlippagePrice(), 8, RoundingMode.HALF_UP));
        }
        if ("short".equals(runningOrder.getLongOrShort())) {
            return runningOrder.getBookAmount().multiply(new BigDecimal(runningOrder.getBookMultiple()))
                .multiply(runningOrder.getBookSlippagePrice().subtract(close).divide(runningOrder.getBookSlippagePrice(), 8, RoundingMode.HALF_UP));
        }
        return BigDecimal.ZERO;
    }

    private OrderDO findRunningOrder(String strategyId, String cryptocurrency) {
        return orderDAO.queryByStrategyId(strategyId, cryptocurrency, 0);
    }

    private void closeOrder(OrderDO runningOrder, RealTimeStrategyDO realTimeStrategy, BigDecimal closePrice, BigDecimal newestPrice) {
        runningOrder.setCloseTime(new Date());
        runningOrder.setClosePrice(closePrice);
        runningOrder.setCloseAmount(runningOrder.getBookAmount());
        BigDecimal totalRevenue = buildTotalRevenue(runningOrder, closePrice);
        runningOrder.setTotalRevenue(totalRevenue);
        runningOrder.setTotalRate(buildTotalRate(runningOrder, totalRevenue));
        runningOrder.setStatus(1);
        // 平仓佣金 = 订单金额 * 杠杆倍数 / 1000
        // 10 * 30 / 500 = 0.5
        runningOrder.setCloseCommission(runningOrder.getBookAmount().multiply(new BigDecimal(runningOrder.getBookMultiple())).divide(new BigDecimal(500), 8, RoundingMode.HALF_UP));
        // 平仓滑点价格
        runningOrder.setCloseSlippagePrice(newestPrice);
        // 下单滑点 = closeSlippagePrice - closePrice
        runningOrder.setCloseSlippage(runningOrder.getCloseSlippagePrice().subtract(runningOrder.getClosePrice()));
        // 平仓佣金 closeCommission
        runningOrder.setOrderCommission(runningOrder.getBookCommission().add(runningOrder.getCloseCommission()));
        // 去除佣金收益额
        runningOrder.setRealRevenue(runningOrder.getTotalRevenue().subtract(runningOrder.getOrderCommission()));
        // 真实平仓收益率
        runningOrder.setRealRate(buildTotalRate(runningOrder, runningOrder.getRealRevenue()));

        // 计算滑点情况下收益额、收益率
        runningOrder.setSlippageTotalRevenue(buildTotalRevenueForSlippage(runningOrder, runningOrder.getCloseSlippagePrice()));
        runningOrder.setSlippageTotalRate(buildTotalRate(runningOrder, runningOrder.getSlippageTotalRevenue()));
        runningOrder.setSlippageRealTotalRevenue(runningOrder.getSlippageTotalRevenue().subtract(runningOrder.getCloseCommission()));
        runningOrder.setSlippageRealTotalRate(buildTotalRate(runningOrder, runningOrder.getSlippageRealTotalRevenue()));

        orderDAO.update(runningOrder);

        // 更新更新策略增额
        realTimeStrategy.setCurrentAmount(realTimeStrategy.getCurrentAmount().add(runningOrder.getTotalRevenue()));
        realTimeStrategy
            .setCurrentSlippageAmount(Optional.ofNullable(realTimeStrategy.getCurrentSlippageAmount()).orElse(BigDecimal.ZERO).add(runningOrder.getSlippageTotalRevenue()));
        realTimeStrategy.setCommission(Optional.ofNullable(realTimeStrategy.getCommission()).orElse(BigDecimal.ZERO).add(runningOrder.getOrderCommission()));
        realTimeStrategyDAO.update(realTimeStrategy);
    }

    private BigDecimal buildTotalRate(OrderDO runningOrder, BigDecimal totalRevenue) {
        return totalRevenue.divide(runningOrder.getBookAmount(), 8, RoundingMode.HALF_UP);
    }

    private OrderDO bookOrder(RealTimeStrategyDO realTimeStrategy, String cryptocurrency, MarketInfo currMarketInfo, WarningResult result, String longOrShort) {
        System.out.println("bookOrder currMarketInfo:" + JSONUtil.toJsonStr(currMarketInfo));
        System.out.println("bookOrder result:" + JSONUtil.toJsonStr(result));
        OrderDO orderDO = new OrderDO();
        orderDO.setStrategyId(realTimeStrategy.getStrategyId());
        orderDO.setOrderSerialNo(PrefixUtils.genOrderNo(realTimeStrategy.getMemberId()));
        orderDO.setTradeCrypto(cryptocurrency);
        orderDO.setTradeExchange(realTimeStrategy.getExchange());
        orderDO.setBookTime(new Date());
        orderDO.setBookPrice(currMarketInfo.getClose());
        orderDO.setBookMultiple(Integer.valueOf(result.getLeverage()));
        orderDO.setBookCommission(BigDecimal.ZERO);
        orderDO.setBookAmount(result.thisOrderTotal());
        orderDO.setStatus(0);
        orderDO.setMemberId(realTimeStrategy.getMemberId());
        orderDO.setLongOrShort(longOrShort);
        orderDO.setTargetTakeProfit(result.getProfit_target());
        orderDO.setTargetStopLoss(result.getStop_loss());
        // 下单佣金 = 订单金额 * 杠杆倍数 / 500
        orderDO.setBookCommission(orderDO.getBookAmount().multiply(new BigDecimal(orderDO.getBookMultiple())).divide(new BigDecimal(500), 8, RoundingMode.HALF_UP));
        // 下单滑点价格
        orderDO.setBookSlippagePrice(currMarketInfo.getNewestPrice());
        // 下单滑点 = bookSlippagePrice - bookPrice
        orderDO.setBookSlippage(orderDO.getBookSlippagePrice().subtract(orderDO.getBookPrice()));
        orderDAO.insert(orderDO);
        return orderDO;
    }

}