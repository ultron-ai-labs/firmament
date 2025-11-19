package cn.org.ultronai.firmament.admin.biz.model.order;

import cn.org.ultronai.firmament.admin.biz.model.BasePageReq;
import lombok.Data;

/**
 * 平仓的数据从数据库中取值
 * 未平仓的数据需要合并当前价格数据
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/08 17:07
 */
@Data
public class OrderReq extends BasePageReq {
    /**
     * RealTimeStrategyDO#strategyId
     */
    private String strategyId;
    private String tradeCrypto;
    // （long/short）
    private String longOrShort;
    // （profit/loss）
    private String profitStatus;
    // （0:未平仓, 1:已平仓）
    private String closeStatus;
}
