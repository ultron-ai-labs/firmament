package cn.org.ultronai.firmament.admin.biz.service.impl;

import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.org.ultronai.firmament.admin.biz.model.exchange.klineHistoryPageListResp;
import cn.org.ultronai.firmament.admin.biz.model.exchange.klineHistoryReq;
import cn.org.ultronai.firmament.admin.biz.model.exchange.klineHistoryResp;
import cn.org.ultronai.firmament.admin.biz.service.ExchangeService;
import cn.org.ultronai.firmament.admin.dal.common.PageList;
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.CryptoOhlcvHistoryDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.CryptoOhlcvHistoryDO;
import cn.org.ultronai.firmament.commonapi.model.CryptoTypeEnum;
import cn.org.ultronai.firmament.commonapi.model.TimeUnitEnum;

/**
 * 交易所服务接口实现类
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/07 17:12
 */
@Service("exchangeService")
public class ExchangeServiceImpl implements ExchangeService {
    @Resource
    private CryptoOhlcvHistoryDAO cryptoOhlcvHistoryDAO;

    /**
     * 获取K线历史数据
     *
     * @param req 请求参数
     * @return K线历史数据
     */
    @Override
    public klineHistoryPageListResp klineHistory(klineHistoryReq req) {
        String exchange = req.getExchange();
        CryptoTypeEnum cryptoType = CryptoTypeEnum.valueOf(req.getCryptoCode());
        TimeUnitEnum timeUnit = TimeUnitEnum.valueOf(req.getTimeUnit());
        Long startTime = req.getStartTime();
        Long endTime = req.getEndTime();
        Integer page = req.getPage();
        Integer pageSize = req.getPageSize();
        PageList<CryptoOhlcvHistoryDO> pageList = cryptoOhlcvHistoryDAO.klineHistory(exchange, cryptoType.getRealCode(exchange), timeUnit.getRealTimeUnit(exchange), startTime,
            endTime, page, pageSize);
        klineHistoryPageListResp resp = new klineHistoryPageListResp();
        resp.setTotal(pageList.getPaginator().getTotalCount());
        resp.setPage(pageList.getPaginator().getCurrentPage());
        resp.setPageSize(pageList.getPaginator().getPageSize());
        resp.setRecords(pageList.getData().stream().map(item -> {
            klineHistoryResp record = new klineHistoryResp();
            record.setCryptoCode(item.getCryptoCode());
            record.setExchange(item.getExchange());
            record.setTimeUnit(item.getTimeUnit());
            record.setOpenTime(item.getOpenTime());
            record.setOpen(item.getOpen().doubleValue());
            record.setHigh(item.getHigh().doubleValue());
            record.setLow(item.getLow().doubleValue());
            record.setClose(item.getClose().doubleValue());
            record.setVolume(item.getVolume().doubleValue());
            return record;
        }).collect(Collectors.toList()));
        return resp;
    }
}
