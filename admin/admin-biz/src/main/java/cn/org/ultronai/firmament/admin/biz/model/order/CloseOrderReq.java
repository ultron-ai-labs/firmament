package cn.org.ultronai.firmament.admin.biz.model.order;

import cn.org.ultronai.firmament.admin.biz.model.BasePageReq;
import lombok.Data;

/**
 * 平仓请求
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/08 17:07
 */
@Data
public class CloseOrderReq extends BasePageReq {
    /**
     * RealTimeStrategyDO#strategyId
     */
    private String strategyId;
    /**
     * 订单流水号
     */
    private String orderSerialNo;
    /**
     * 币种
     */
    private String tradeCrypto;
    /**
     * 交易所
     */
    private String tradeExchange;
    /**
     * long 做多，short 做空
     */
    private String longOrShort;
}
