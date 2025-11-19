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
 *
 * ------------------------------------------------------------------------
 * Coding Power by icanci.
 * ------------------------------------------------------------------------
 */
package cn.org.ultronai.firmament.exchange.okx;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.hutool.core.date.StopWatch;
import cn.hutool.http.Method;
import cn.org.ultronai.firmament.commonapi.http.Client;
import cn.org.ultronai.firmament.commonapi.http.HttpClientImpl;
import cn.org.ultronai.firmament.commonapi.model.CryptoTypeEnum;
import cn.org.ultronai.firmament.commonapi.model.KlineData;
import cn.org.ultronai.firmament.commonapi.model.TimeUnitEnum;
import cn.org.ultronai.firmament.exchange.okx.model.OkxResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * okx api
 * <a href="https://app.okx.com/docs-v5/en/#order-book-trading-market-data-get-candlesticks-history">history Wiki</a>
 *
 * @author icanci
 * @since 1.0 Created in 2025/10/29 13:06
 */
@Slf4j
public class OkxApiTools {
    private static final ObjectMapper objectMapper    = new ObjectMapper();
    /** http instance */
    private static final Client       HTTP_INSTANCE   = HttpClientImpl.getInstance();
    /** 基础URL */
    private static final String       base_url        = "https://www.okx.com";
    /**
     * 获取蜡烛图数据
     * <a href="https://app.okx.com/docs-v5/en/#order-book-trading-market-data-get-candlesticks">candles</a>
     */
    private static final String       candles         = "/api/v5/market/candles";
    /**
     * 获取历史蜡烛图数据
     * <a href="https://app.okx.com/docs-v5/en/#order-book-trading-market-data-get-candlesticks-history">history_candles</a>
     */
    private static final String       history_candles = "/api/v5/market/history-candles";
    /**
     * 获取某个币种当前价格
     * <a href="https://app.okx.com/docs-v5/en/#order-book-trading-market-data-get-ticker">ticker</a>
     */
    private static final String       ticker          = "/api/v5/market/ticker";

    /**
     * 获取蜡烛图数据
     * 
     * {
     *     "code":"0",
     *     "msg":"",
     *     "data":[
     *      [
     *         "1597026383085",
     *         "3.721",
     *         "3.743",
     *         "3.677",
     *         "3.708",
     *         "8422410",
     *         "22698348.04828491",
     *         "12698348.04828491",
     *         "0"
     *     ]
     *     ]
     * }
     * @param coin coin
     * @param timeUnit timeUnit
     * @param limit limit
     * @return 获取蜡烛图数据
     */
    public static List<KlineData> getKlines(CryptoTypeEnum coin, TimeUnitEnum timeUnit, int limit) {
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            String url = base_url + candles + "?instId=" + coin.getOkx() + "&bar=" + timeUnit.getOkx() + "&limit=" + limit;
            Client.RpcRequest rpcRequest = new Client.RpcRequest(url, new HashMap<>(), new HashMap<>(), Method.GET, 1, TimeUnit.MINUTES, 0);
            String responseBody = HTTP_INSTANCE.call(rpcRequest, String.class);
            OkxResponse<List<String>> okxResponse = objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructParametricType(OkxResponse.class, List.class));
            if (!"0".equals(okxResponse.getCode())) {
                throw new Exception("OKX API error: " + okxResponse.getMsg());
            }
            stopWatch.stop();
            log.info("OkxApiTools candles total:" + stopWatch.getTotalTimeSeconds());
            return okxResponse.getData().stream().map(data -> mapper(coin, timeUnit, data)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取历史蜡烛图数据
     * 
     * @param coin coin
     * @param timeUnit timeUnit
     * @param from from
     * @param to to
     * @return 获取历史蜡烛图数据
     */
    public static List<KlineData> getKlinesInRange(CryptoTypeEnum coin, TimeUnitEnum timeUnit, long from, long to) {
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            List<KlineData> result = new ArrayList<>();

            String baseUrl = base_url + history_candles;
            int batchSize = 300; // OKX 单次最大返回条数
            long currentAfter = from; // 从 from 开始拉取

            while (currentAfter < to) {
                String url = baseUrl + "?instId=" + coin.getOkx() + "&bar=" + timeUnit.getOkx() + "&after=" + currentAfter + "&limit=" + batchSize;

                Client.RpcRequest rpcRequest = new Client.RpcRequest(url, new HashMap<>(), new HashMap<>(), Method.GET, 1, TimeUnit.MINUTES, 0);
                String responseBody = HTTP_INSTANCE.call(rpcRequest, String.class);

                // 你的现有反序列化逻辑
                OkxResponse<List<String>> okxResponse = objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructParametricType(OkxResponse.class, List.class));

                if (!"0".equals(okxResponse.getCode())) {
                    throw new Exception("OKX API error: " + okxResponse.getMsg());
                }

                List<List<String>> rawDataList = okxResponse.getData();
                if (rawDataList == null || rawDataList.isEmpty()) {
                    break; // 没有更多数据
                }

                boolean hasMore = false;

                for (List<String> rawData : rawDataList) {
                    // OKX 返回的每一根K线是一个字符串数组的 JSON，如 ["2024-06-01T00:00:00.000Z","60000","61000","59000","60500","100"]
                    // 但你目前的 mapper(coin, timeUnit, data) 应该已经能正确处理这种格式
                    // 所以我们直接传入 rawData（即 List<String> 中的元素是单个 String，但你的 mapper 可能需要的是 List<String>，请确认！）

                    // ⚠️ 注意：这里假设你的 mapper(coin, timeUnit, data) 接收的是单个 K线字符串（即 OKX 返回的数组转成的字符串）
                    // 但通常你的 mapper 可能需要的是整个 List<String>（即 ["t","o","h","l","c","v"]），所以需要确认！

                    // ✅ 如果你的 mapper 是针对单个 K线字符串（如 "[...]"），则直接传入 rawData
                    KlineData kline = mapper(coin, timeUnit, rawData);

                    long klineTime = kline.getOpenTime(); // 假设你的 KlineData 有 getOpenTime() 返回毫秒时间戳

                    if (klineTime >= from && klineTime < to) {
                        result.add(kline);
                        hasMore = true;
                    }

                    // 更新 currentAfter 为当前 K线时间戳 +1，避免重复
                    if (klineTime >= currentAfter) {
                        currentAfter = klineTime + 1;
                    }

                    // 如果当前 K线已经 >= to，则终止循环
                    if (klineTime >= to) {
                        hasMore = false;
                        break;
                    }
                }

                if (!hasMore) {
                    break;
                }
            }

            stopWatch.stop();
            log.info("OkxApiTools getKlinesInRange total: {}ms, from={} to={}, count={}", stopWatch.getTotalTimeMillis(), from, to, result.size());

            return result;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch Klines in range: " + e.getMessage(), e);
        }
    }

    private static KlineData mapper(CryptoTypeEnum coin, TimeUnitEnum timeUnit, List<String> data) {
        KlineData klineData = new KlineData();
        klineData.setCryptoCode(coin.getOkx());
        klineData.setTimeUnit(timeUnit.getOkx());
        klineData.setOpenTime(Long.parseLong(data.get(0)));
        klineData.setOpen(new BigDecimal(data.get(1)));
        klineData.setHigh(new BigDecimal(data.get(2)));
        klineData.setLow(new BigDecimal(data.get(3)));
        klineData.setClose(new BigDecimal(data.get(4)));
        klineData.setVolume(new BigDecimal(data.get(5)));
        klineData.setEnd(Integer.parseInt(data.get(8)) == 1);
        klineData.setExchange("okx");
        klineData.setCryptoCode("okx" + "-" + coin.getOkx());
        klineData.setTimeUnit(timeUnit.getBinance());
        return klineData;
    }
}
