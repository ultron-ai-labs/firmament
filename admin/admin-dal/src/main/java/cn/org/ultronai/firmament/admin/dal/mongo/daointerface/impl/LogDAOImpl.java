package cn.org.ultronai.firmament.admin.dal.mongo.daointerface.impl;

import org.springframework.stereotype.Service;

import cn.org.ultronai.firmament.admin.dal.mongo.MongoDocuments;
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.LogDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.LogDO;
import cn.org.ultronai.firmament.admin.dal.mongo.mongo.AbstractBaseDAO;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 20:52
 */
@Service("logDAO")
public class LogDAOImpl extends AbstractBaseDAO<LogDO> implements LogDAO {
    /**
     * 获取实体类类型
     * 子类需要实现此方法返回具体的实体类class
     *
     * @return 实体类class
     */
    @Override
    protected Class<LogDO> getEntityClass() {
        return LogDO.class;
    }

    /**
     * 获取集合名称
     * 子类需要实现此方法返回对应的集合名称
     *
     * @return 集合名称
     */
    @Override
    protected String getCollectionName() {
        return MongoDocuments.FIRMAMENT_LOG;
    }
}
