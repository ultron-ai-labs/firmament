package cn.org.ultronai.firmament.admin.biz.service;

import cn.org.ultronai.firmament.admin.biz.model.exchange.klineHistoryPageListResp;
import cn.org.ultronai.firmament.admin.biz.model.exchange.klineHistoryReq;

/**
 * 交易所服务接口
 *
 * @author icanci
 * @since 1.0 Created in 2025/11/07 17:06
 */
public interface ExchangeService {
    /**
     * 获取K线历史数据
     *
     * @param req 请求参数
     * @return K线历史数据
     */
    klineHistoryPageListResp klineHistory(klineHistoryReq req);
}
