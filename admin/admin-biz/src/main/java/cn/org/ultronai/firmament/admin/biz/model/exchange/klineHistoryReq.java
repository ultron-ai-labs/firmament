package cn.org.ultronai.firmament.admin.biz.model.exchange;

import cn.org.ultronai.firmament.admin.biz.model.BasePageReq;
import lombok.Data;

/**
 * k线历史数据请求参数
 * 
 * @param {string} params.exchange - 交易所
 * @param {string} params.cryptoCode - 币种代码
 * @param {string} params.timeUnit - 时间单位
 * @param {number} params.startTime - 开始时间戳
 * @param {number} params.endTime - 结束时间戳
 * @param {number} params.page - 页码
 * @param {number} params.pageSize - 每页大小
 * @author icanci
 * @since 1.0 Created in 2025/11/07 17:02
 */
@Data
public class klineHistoryReq extends BasePageReq {
    /**
     * 交易所
     */
    private String exchange;
    /**
     * 币种代码
     */
    private String cryptoCode;
    /**
     * 时间单位
     */
    private String timeUnit;
    /**
     * 开始时间戳
     */
    private Long   startTime;
    /**
     * 结束时间戳
     */
    private Long   endTime;
}
