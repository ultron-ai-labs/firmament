package cn.org.ultronai.firmament.admin.dal.mongo.daointerface;

import java.util.List;

import cn.org.ultronai.firmament.admin.dal.common.PageList;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.CryptoOhlcvHistoryDO;

/**
 * CryptoOhlcvHistoryDO数据访问接口
 *
 * @author kai
 * @since 1.0 Created in 2025/11/03
 */
public interface CryptoOhlcvHistoryDAO extends BaseDAO<CryptoOhlcvHistoryDO> {

    /**
     * 根据cryptoCode查询历史数据
     *
     * @param cryptoCode 币种代码
     * @return 历史数据列表
     */
    List<CryptoOhlcvHistoryDO> findByCryptoCode(String cryptoCode);

    /**
     * 根据cryptoCode和timeUnit查询历史数据
     *
     * @param cryptoCode 币种代码
     * @param timeUnit 时间单位
     * @return 历史数据列表
     */
    List<CryptoOhlcvHistoryDO> findByCryptoCodeAndTimeUnit(String cryptoCode, String timeUnit);

    /**
     * 查询唯一的一条记录
     * 
     * @param cryptoCode cryptoCode
     * @param timeUnit timeUnit
     * @param exchange exchange
     * @param openTime openTime
     * @return CryptoOhlcvHistoryDO
     */
    CryptoOhlcvHistoryDO findUnique(String cryptoCode, String timeUnit, String exchange, Long openTime);

    /**
     * 根据cryptoCode、timeUnit和时间范围查询历史数据
     *
     * @param cryptoCode 币种代码
     * @param timeUnit 时间单位
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageList<CryptoOhlcvHistoryDO> findByCryptoCodeAndTimeUnitAndTimeRange(String cryptoCode, String timeUnit, Long startTime, Long endTime, int pageNum, int pageSize);

    /**
     * 根据cryptoCode、timeUnit和时间范围查询历史数据
     *
     * @param cryptoCode 币种代码
     * @param timeUnit 时间单位
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 查询结果
     */
    List<CryptoOhlcvHistoryDO> findByCryptoCodeAndTimeUnitAndTimeRange(String cryptoCode, String timeUnit, Long startTime, Long endTime);

    /**
     * 查询K线历史数据
     *
     * @param exchange 交易所
     * @param realCode 币种代码
     * @param timeUnit 时间单位
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param page 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageList<CryptoOhlcvHistoryDO> klineHistory(String exchange, String realCode, String timeUnit, Long startTime, Long endTime, Integer page, Integer pageSize);
}