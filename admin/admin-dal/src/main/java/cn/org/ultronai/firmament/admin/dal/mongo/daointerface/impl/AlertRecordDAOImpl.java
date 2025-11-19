package cn.org.ultronai.firmament.admin.dal.mongo.daointerface.impl;

import org.springframework.stereotype.Service;

import cn.org.ultronai.firmament.admin.dal.mongo.MongoDocuments;
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.AlertRecordDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.AlertRecordDO;
import cn.org.ultronai.firmament.admin.dal.mongo.mongo.AbstractBaseDAO;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/06 08:34
 */
@Service("alertRecordDAO")
public class AlertRecordDAOImpl extends AbstractBaseDAO<AlertRecordDO> implements AlertRecordDAO {
    /**
     * 获取实体类类型
     * 子类需要实现此方法返回具体的实体类class
     *
     * @return 实体类class
     */
    @Override
    protected Class<AlertRecordDO> getEntityClass() {
        return AlertRecordDO.class;
    }

    /**
     * 获取集合名称
     * 子类需要实现此方法返回对应的集合名称
     *
     * @return 集合名称
     */
    @Override
    protected String getCollectionName() {
        return MongoDocuments.FIRMAMENT_ALERT_RECORD;
    }
}
