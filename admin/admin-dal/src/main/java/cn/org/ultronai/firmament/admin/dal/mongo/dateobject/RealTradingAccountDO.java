package cn.org.ultronai.firmament.admin.dal.mongo.dateobject;

import lombok.Data;

/**
 * 用户交易账户数据
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/14 15:33
 */
@Data
public class RealTradingAccountDO extends BaseDO {
    /**
     * 交易所
     */
    private String exchange;
    /**
     * 账户apiKey
     */
    private String apiKey;
    /**
     * 账户secretKey
     */
    private String secretKey;
    /**
     * 账户apiName
     */
    private String apiName;
    /**
     * 账户apiPassphrase
     */
    private String passphrase;
    /**
     * 是否模拟盘：0不是，1：是
     */
    private int    simulation;
}
