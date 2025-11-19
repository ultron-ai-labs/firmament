package cn.org.ultronai.firmament.admin.dal.mongo.daointerface.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.org.ultronai.firmament.admin.dal.common.PageList;
import cn.org.ultronai.firmament.admin.dal.mongo.MongoDocuments;
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.CryptoOhlcvHistoryDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.CryptoOhlcvHistoryDO;
import cn.org.ultronai.firmament.admin.dal.mongo.mongo.AbstractBaseDAO;

/**
 * CryptoOhlcvHistoryDO数据访问实现类
 *
 * @author kai
 * @since 1.0 Created in 2025/11/03
 */
@Service("mongoCryptoOhlcvHistoryDAO")
public class MongoCryptoOhlcvHistoryDAOImpl extends AbstractBaseDAO<CryptoOhlcvHistoryDO> implements CryptoOhlcvHistoryDAO {

    @Override
    protected Class<CryptoOhlcvHistoryDO> getEntityClass() {
        return CryptoOhlcvHistoryDO.class;
    }

    @Override
    protected String getCollectionName() {
        return MongoDocuments.FIRMAMENT_CRYPTO_OHLCV_HISTORY;
    }

    @Override
    public List<CryptoOhlcvHistoryDO> queryAll() {
        Query query = new Query(Criteria.where("deleted").is(false));
        return mongoTemplate.find(query, CryptoOhlcvHistoryDO.class, getCollectionName());
    }

    @Override
    public CryptoOhlcvHistoryDO queryOneById(String _id) {
        Query query = new Query(Criteria.where("id").is(_id).and("deleted").is(false));
        return mongoTemplate.findOne(query, CryptoOhlcvHistoryDO.class, getCollectionName());
    }

    @Override
    public PageList<CryptoOhlcvHistoryDO> pageQuery(CryptoOhlcvHistoryDO t, int pageNum, int pageSize) {
        Query query = new Query();
        if (t != null) {
            if (t.getCryptoCode() != null) {
                query.addCriteria(Criteria.where("cryptoCode").is(t.getCryptoCode()));
            }
            if (t.getTimeUnit() != null) {
                query.addCriteria(Criteria.where("timeUnit").is(t.getTimeUnit()));
            }
            if (t.getExchange() != null) {
                query.addCriteria(Criteria.where("exchange").is(t.getExchange()));
            }
        }
        query.addCriteria(Criteria.where("deleted").is(false));
        return pageQuery(query, CryptoOhlcvHistoryDO.class, pageSize, pageNum, getCollectionName());
    }

    @Override
    public List<CryptoOhlcvHistoryDO> findByCryptoCode(String cryptoCode) {
        Query query = new Query(Criteria.where("cryptoCode").is(cryptoCode).and("deleted").is(false));
        return mongoTemplate.find(query, CryptoOhlcvHistoryDO.class, getCollectionName());
    }

    @Override
    public List<CryptoOhlcvHistoryDO> findByCryptoCodeAndTimeUnit(String cryptoCode, String timeUnit) {
        Query query = new Query(Criteria.where("cryptoCode").is(cryptoCode).and("timeUnit").is(timeUnit).and("deleted").is(false));
        return mongoTemplate.find(query, CryptoOhlcvHistoryDO.class, getCollectionName());
    }

    /**
     * 查询唯一的一条记录
     *
     * @param cryptoCode cryptoCode
     * @param timeUnit   timeUnit
     * @param exchange   exchange
     * @param openTime   openTime
     * @return CryptoOhlcvHistoryDO
     */
    @Override
    public CryptoOhlcvHistoryDO findUnique(String cryptoCode, String timeUnit, String exchange, Long openTime) {
        Query query = new Query(
            Criteria.where("cryptoCode").is(cryptoCode).and("timeUnit").is(timeUnit).and("exchange").is(exchange).and("openTime").is(openTime).and("deleted").is(false));
        return mongoTemplate.findOne(query, CryptoOhlcvHistoryDO.class, getCollectionName());
    }

    @Override
    public PageList<CryptoOhlcvHistoryDO> findByCryptoCodeAndTimeUnitAndTimeRange(String cryptoCode, String timeUnit, Long startTime, Long endTime, int pageNum, int pageSize) {
        Criteria criteria = Criteria.where("cryptoCode").is(cryptoCode).and("timeUnit").is(timeUnit).and("deleted").is(false);

        if (startTime != null) {
            criteria.and("openTime").gte(startTime);
        }

        if (endTime != null) {
            criteria.and("closeTime").lte(endTime);
        }

        Query query = new Query(criteria);
        return pageQuery(query, CryptoOhlcvHistoryDO.class, pageSize, pageNum, getCollectionName());
    }

    /**
     * 根据cryptoCode、timeUnit和时间范围查询历史数据
     *
     * @param cryptoCode 币种代码
     * @param timeUnit   时间单位
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 查询结果
     */
    @Override
    public List<CryptoOhlcvHistoryDO> findByCryptoCodeAndTimeUnitAndTimeRange(String cryptoCode, String timeUnit, Long startTime, Long endTime) {
        Criteria criteria = Criteria.where("cryptoCode").is(cryptoCode).and("timeUnit").is(timeUnit).and("deleted").is(false);
        if (startTime != null) {
            criteria.and("openTime").gte(startTime);
        }
        if (endTime != null) {
            criteria.and("openTime").lte(endTime);
        }
        Query query = new Query(criteria);
        return mongoTemplate.find(query, CryptoOhlcvHistoryDO.class, getCollectionName());
    }

    /**
     * 查询K线历史数据
     *
     * @param exchange  交易所
     * @param realCode  币种代码
     * @param timeUnit  时间单位
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param page      页码
     * @param pageSize  每页大小
     * @return 分页结果
     */
    @Override
    public PageList<CryptoOhlcvHistoryDO> klineHistory(String exchange, String realCode, String timeUnit, Long startTime, Long endTime, Integer page, Integer pageSize) {
        Criteria criteria = Criteria.where("exchange").is(exchange).and("cryptoCode").is(realCode).and("timeUnit").is(timeUnit).and("deleted").is(false);
        criteria.and("openTime").gte(startTime).lte(endTime);
        // 根据opentime 进行排序
        Query query = new Query(criteria);
        query.with(Sort.by(Sort.Direction.DESC, "openTime"));
        return pageQuery(query, CryptoOhlcvHistoryDO.class, pageSize, page, getCollectionName());
    }
}