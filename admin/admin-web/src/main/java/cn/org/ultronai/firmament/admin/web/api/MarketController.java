package cn.org.ultronai.firmament.admin.web.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.org.ultronai.firmament.admin.biz.model.exchange.klineHistoryReq;
import cn.org.ultronai.firmament.admin.biz.service.ExchangeService;
import cn.org.ultronai.firmament.admin.dal.common.R;

/**
 * okx行情接口
 *
 * @author icanci
 * @since 1.0 Created in 2025/11/07 10:09
 */
@RestController
@RequestMapping("/api/exchange")
public class MarketController {
    @Resource
    private ExchangeService exchangeService;

    /**
     * 获取K线历史数据
     * 
     * @return K线历史数据
     */
    @PostMapping("/klineHistory")
    public R klineHistory(@RequestBody klineHistoryReq req) {
        return R.builderOk().data(exchangeService.klineHistory(req)).build();
    }
}
