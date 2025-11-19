package cn.org.ultronai.firmament.exchange.binance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.org.ultronai.firmament.commonapi.model.CryptoTypeEnum;
import cn.org.ultronai.firmament.commonapi.model.KlineData;
import cn.org.ultronai.firmament.commonapi.model.TimeUnitEnum;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/06 09:30
 */
public class BinanceResponseParser {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 解析Binance API返回的K线数据字符串为CryptoklineData对象列表
     * 字符串格式: [[1762392600000,"102833.83000000","102894.00000000","102811.02000000","102888.70000000","11.77479000",1762392899999,"1211149.55012360",3328,"2.95470000","303923.46192780","0"]]
     * 错误格式: {"code": -1120, "msg": "Invalid interval."}
     * 
     * @param responseBody Binance API返回的K线数据JSON字符串
     * @return 解析后的CryptoklineData对象列表
     */
    public static List<KlineData> parseKlines(CryptoTypeEnum cryptoType, TimeUnitEnum timeUnit, String responseBody) {
        List<KlineData> resultList = new ArrayList<>();
        try {
            // 解析JSON字符串为JsonNode
            JsonNode rootNode = objectMapper.readTree(responseBody);

            // 检查是否是错误响应格式 {"code": -1120, "msg": "Invalid interval."}
            if (rootNode.has("code") && rootNode.has("msg")) {
                int errorCode = rootNode.get("code").asInt();
                String errorMsg = rootNode.get("msg").asText();
                System.err.println("Binance API Error: Code=" + errorCode + ", Message=" + errorMsg);
                return new ArrayList<>();
            }

            // 遍历每个K线数据（正常响应格式）

            for (JsonNode klineNode : rootNode) {
                KlineData klineData = new KlineData();

                // 映射Binance K线数据到CryptoklineData对象
                // 字段对应关系：
                // 0: 开盘时间
                // 1: 开盘价
                // 2: 最高价
                // 3: 最低价
                // 4: 收盘价
                // 5: 成交量
                // 6: 收盘时间
                // 7: 成交额
                // 8: 成交笔数
                // 9: 主动买入成交量
                // 10: 主动买入成交额
                // 11: 忽略该参数

                klineData.setOpenTime(klineNode.get(0).asLong());
                klineData.setOpen(new BigDecimal(klineNode.get(1).asText()));
                klineData.setHigh(new BigDecimal(klineNode.get(2).asText()));
                klineData.setLow(new BigDecimal(klineNode.get(3).asText()));
                klineData.setClose(new BigDecimal(klineNode.get(4).asText()));
                klineData.setVolume(new BigDecimal(klineNode.get(5).asText()));
                klineData.setCloseTime(klineNode.get(6).asLong());
                // 当前时间大于收盘时间，则认为收盘了
                klineData.setEnd(System.currentTimeMillis() > klineNode.get(6).asLong());
                // 设置默认交易所为binance
                klineData.setExchange("binance");
                klineData.setCryptoCode("binance" + "-" + cryptoType.getBinance());
                klineData.setTimeUnit(timeUnit.getBinance());

                // 添加到结果列表
                resultList.add(klineData);
            }
        } catch (Exception e) {
            // 解析失败时记录异常并返回空列表
            e.printStackTrace();
            return new ArrayList<>();
        }

        return resultList;
    }
}
