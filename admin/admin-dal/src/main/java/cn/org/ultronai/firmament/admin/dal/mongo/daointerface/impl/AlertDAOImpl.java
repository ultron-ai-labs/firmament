package cn.org.ultronai.firmament.admin.dal.mongo.daointerface.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.org.ultronai.firmament.admin.dal.common.PageList;
import cn.org.ultronai.firmament.admin.dal.mongo.MongoDocuments;
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.AlertDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.AlertDO;
import cn.org.ultronai.firmament.admin.dal.mongo.mongo.AbstractBaseDAO;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/06 08:32
 */
@Service("alertDAO")
public class AlertDAOImpl extends AbstractBaseDAO<AlertDO> implements AlertDAO {
    /**
     * 获取实体类类型
     * 子类需要实现此方法返回具体的实体类class
     *
     * @return 实体类class
     */
    @Override
    protected Class<AlertDO> getEntityClass() {
        return AlertDO.class;
    }

    /**
     * 获取集合名称
     * 子类需要实现此方法返回对应的集合名称
     *
     * @return 集合名称
     */
    @Override
    protected String getCollectionName() {
        return MongoDocuments.FIRMAMENT_ALERT;
    }

    /**
     * 根据会员ID查询告警信息
     *
     * @param memberId  会员ID
     * @param alertName 告警名称
     * @param alertType 告警类型
     * @param page      页码
     * @param pageSize  每页大小
     * @return 告警信息
     */
    @Override
    public PageList<AlertDO> queryByMemberId(String memberId, String alertName, String alertType, Integer page, Integer pageSize) {
        // name 模糊查询
        Criteria criteria = Criteria.where("deleted").is(false);

        if (StringUtils.isNotBlank(alertType)) {
            // 不分区大小写查询，其中操作符"i"：表示不分区大小写
            criteria.and("alertType").is(alertType);
        }
        if (StringUtils.isNotBlank(alertName)) {
            // 不分区大小写查询，其中操作符"i"：表示不分区大小写
            criteria.and("alertName").regex("^.*" + alertName + ".*$", "i");
        }

        criteria.and("memberId").is(memberId);

        Query query = new Query(criteria);
        query.with(Sort.by(Sort.Direction.DESC, "updateTime"));

        return pageQuery(query, getEntityClass(), pageSize, page, getCollectionName());
    }

    /**
     * 根据告警ID查询告警信息
     *
     * @param alertId 告警ID
     * @return 告警信息
     */
    @Override
    public AlertDO queryByAlertId(String alertId) {
        Query query = new Query(Criteria.where("alertId").is(alertId).and("deleted").is(false));
        return mongoTemplate.findOne(query, getEntityClass(), getCollectionName());
    }

    /**
     * 根据会员ID查询告警信息
     *
     * @param memberId 会员ID
     * @return 告警信息
     */
    @Override
    public List<AlertDO> queryByMemberId(String memberId) {
        Query query = new Query(Criteria.where("memberId").is(memberId).and("deleted").is(false));
        return mongoTemplate.find(query, getEntityClass(), getCollectionName());
    }

    /**
     * 根据告警ID删除告警信息
     *
     * @param alertId 告警ID
     */
    @Override
    public void deleteByAlertId(String alertId) {
        Query query = new Query(Criteria.where("alertId").is(alertId));
        mongoTemplate.remove(query, getEntityClass(), getCollectionName());
    }

    /**
     * 根据告警名称查询告警信息
     *
     * @param memberId  会员ID
     * @param alertName 告警名称
     * @return 告警信息
     */
    @Override
    public AlertDO queryByAlertName(String memberId, String alertName) {
        Query query = new Query(Criteria.where("memberId").is(memberId).and("alertName").is(alertName).and("deleted").is(false));
        return mongoTemplate.findOne(query, getEntityClass(), getCollectionName());
    }
}
