package cn.org.ultronai.firmament.admin.biz.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import cn.org.ultronai.firmament.admin.biz.model.realtimestrategy.WarningResult;
import cn.org.ultronai.firmament.admin.biz.prompt.model.AccountInfo;
import cn.org.ultronai.firmament.admin.biz.prompt.model.MarketInfo;
import cn.org.ultronai.firmament.admin.biz.repository.KlineRepositoryHolder;
import cn.org.ultronai.firmament.admin.biz.service.*;
import cn.org.ultronai.firmament.admin.biz.service.runner.OrderRunner;
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.ModelDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.PromptDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.ModelDO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.PromptDO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.RealTimeStrategyDO;
import cn.org.ultronai.firmament.aichat.AIModelTypeEnum;
import cn.org.ultronai.firmament.aichat.AiChatService;
import cn.org.ultronai.firmament.aichat.AiChatServiceProxy;
import cn.org.ultronai.firmament.aichat.trade.ChatWrapper;
import cn.org.ultronai.firmament.commonapi.model.TimeUnitEnum;
import cn.org.ultronai.firmament.support.indicator.model.OHLCV;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 19:00
 */
@Service("aiTradingService")
public class AITradingServiceImpl implements AITradingService {
    @Resource
    private LogService                      logService;
    @Resource
    private ModelDAO                        modelDAO;
    @Resource
    private OrderService                    orderService;
    @Resource
    private MarketService                   marketService;
    @Resource
    private PromptDAO                       promptDAO;
    @Resource
    private PromptAssemblyService           promptAssemblyService;
    private static final ThreadPoolExecutor ORDER_POOL = new ThreadPoolExecutor(40,            //
        120,                                                                                   //
        60L,                                                                                   //
        TimeUnit.SECONDS,                                                                      //
        new LinkedBlockingQueue<>(2000),                                                       //
        runnable -> new Thread(runnable, "AbstractRetryClient Pool-" + runnable.hashCode()),   //
        (r, executor) -> {
            r.run();
        });

    /**
     * AI交易
     *
     * @param realTimeStrategy realTimeStrategy
     */
    @Override
    public void aiTrading(RealTimeStrategyDO realTimeStrategy) {
        // ai 交易步骤
        PromptDO promptDO = promptDAO.queryByPromptId(realTimeStrategy.getPromptStrategyId());
        if (promptDO == null) {
            throw new RuntimeException("提示词不存在");
        }
        // 0、查询当前的订单数据
        AccountInfo orderInfo = orderService.queryOrderInfo(realTimeStrategy);
        // 1、获取目标交易所目标币种目标周期的300根k线数据
        Map<String, List<OHLCV>> currentMarketOHLCV = KlineRepositoryHolder.getSortedKlineData(realTimeStrategy.getExchange(), realTimeStrategy.getCryptocurrencies(),
            TimeUnitEnum.valueOf(realTimeStrategy.getTimeUnit()));

        List<MarketInfo> currentMarketInfos = marketService.queryMarketInfo(currentMarketOHLCV, realTimeStrategy.getExchange(),
            TimeUnitEnum.valueOf(realTimeStrategy.getTimeUnit()));

        // 2.计算指标，封装提示词数据
        // 3.调用模型
        String prompt = promptAssemblyService.getRealPrompt(currentMarketInfos, orderInfo, promptDO);
        // 4.根据模型返回结果，生成订单/或平仓订单
        ModelDO modelDO = modelDAO.queryByModelId(realTimeStrategy.getMemberId(), realTimeStrategy.getModelId());
        if (modelDO == null) {
            throw new RuntimeException("模型不存在");
        }
        AIModelTypeEnum aiModel = AIModelTypeEnum.getByModelType(modelDO.getModelType());
        if (aiModel == null) {
            throw new RuntimeException("模型类型错误");
        }
        AiChatService aiChat = AiChatServiceProxy.getAiChat(aiModel);
        // 调用模型
        ChatWrapper wrapper = aiChat.chat(modelDO.getApiUrl(), modelDO.getApiKey(), realTimeStrategy.getModelCategory(), promptDO.getSystemPrompt(), prompt);
        // 记录日志
        logService.logChatReq(realTimeStrategy.getMemberId(), realTimeStrategy.getStrategyId(), wrapper.getReqModel(), wrapper.getReqChatMessages());
        logService.logChatResp(realTimeStrategy.getMemberId(), realTimeStrategy.getStrategyId(), wrapper.getRespModel(), wrapper.getRespChatChoices());
        if (CollectionUtils.isEmpty(wrapper.getRespChatChoices())) {
            return;
        }

        JSON parse = JSONUtil.parse(wrapper.getRespContent());
        // 市场数据
        Map<String, MarketInfo> marketInfo = currentMarketInfos.stream().collect(Collectors.toMap(MarketInfo::getCoin, v -> v));
        // 最新价格数据
        Map<String, BigDecimal> newestMarket = marketService.queryNewestMarketInfo(realTimeStrategy.getExchange(), realTimeStrategy.getCryptocurrencies());
        // 遍历数据，进行处理
        for (String cryptocurrency : realTimeStrategy.getCryptocurrencies()) {
            WarningResult result = parse.getByPath(cryptocurrency, WarningResult.class);
            MarketInfo currMarketInfo = marketInfo.get(cryptocurrency);
            currMarketInfo.setNewestPrice(newestMarket.get(cryptocurrency) == null ? currMarketInfo.getClose() : newestMarket.get(cryptocurrency));
            FutureTask<Object> task = new FutureTask<>(new OrderRunner(orderService, result, currMarketInfo, realTimeStrategy, cryptocurrency));
            ORDER_POOL.execute(task);
        }
    }
}
