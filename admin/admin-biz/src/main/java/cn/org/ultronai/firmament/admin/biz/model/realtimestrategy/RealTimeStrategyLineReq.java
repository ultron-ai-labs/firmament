package cn.org.ultronai.firmament.admin.biz.model.realtimestrategy;

import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/10 21:01
 */
@Data
public class RealTimeStrategyLineReq {
    private String strategyId;
    /** 多少天的数据 */
    private String type;
}
