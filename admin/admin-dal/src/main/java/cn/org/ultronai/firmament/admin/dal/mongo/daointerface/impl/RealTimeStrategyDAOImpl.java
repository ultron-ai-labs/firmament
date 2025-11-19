package cn.org.ultronai.firmament.admin.dal.mongo.daointerface.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.org.ultronai.firmament.admin.dal.mongo.MongoDocuments;
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.RealTimeStrategyDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.RealTimeStrategyDO;
import cn.org.ultronai.firmament.admin.dal.mongo.mongo.AbstractBaseDAO;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 17:34
 */
@Service("realTimeStrategyDAO")
public class RealTimeStrategyDAOImpl extends AbstractBaseDAO<RealTimeStrategyDO> implements RealTimeStrategyDAO {
    /**
     * 获取实体类类型
     * 子类需要实现此方法返回具体的实体类class
     *
     * @return 实体类class
     */
    @Override
    protected Class<RealTimeStrategyDO> getEntityClass() {
        return RealTimeStrategyDO.class;
    }

    /**
     * 获取集合名称
     * 子类需要实现此方法返回对应的集合名称
     *
     * @return 集合名称
     */
    @Override
    protected String getCollectionName() {
        return MongoDocuments.FIRMAMENT_REAL_TIME_STRATEGY;
    }

    /**
     * 根据会员id查询实时数据模型
     *
     * @param memberId   会员id
     * @param strategyId
     * @return 实时数据模型
     */
    @Override
    public List<RealTimeStrategyDO> queryByMemberId(String memberId, String strategyId) {
        Criteria criteria = Criteria.where("memberId").is(memberId).and("deleted").is(false);
        if (StringUtils.isNotBlank(strategyId)) {
            criteria.and("strategyId").is(strategyId);
        }
        Query query = new Query(criteria);
        query.with(Sort.by(Sort.Direction.DESC, "updateTime"));
        return mongoTemplate.find(query, getEntityClass(), getCollectionName());
    }

    /**
     * 根据策略id查询实时数据模型
     *
     * @param strategyId 策略id
     * @return 策略id
     */
    @Override
    public RealTimeStrategyDO queryByStrategyId(String strategyId) {
        Criteria criteria = Criteria.where("strategyId").is(strategyId).and("deleted").is(false);
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, getEntityClass(), getCollectionName());
    }

    /**
     * 根据策略名称查询实时数据模型
     *
     * @param memberId     会员id
     * @param strategyName 策略名称
     * @return 策略名称
     */
    @Override
    public RealTimeStrategyDO queryByName(String memberId, String strategyName) {
        Criteria criteria = Criteria.where("memberId").is(memberId).and("strategyName").is(strategyName).and("deleted").is(false);
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, getEntityClass(), getCollectionName());
    }
}
