package cn.org.ultronai.firmament.admin.dal.mongo.daointerface.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.org.ultronai.firmament.admin.dal.common.PageList;
import cn.org.ultronai.firmament.admin.dal.mongo.MongoDocuments;
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.PromptDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.PromptDO;
import cn.org.ultronai.firmament.admin.dal.mongo.mongo.AbstractBaseDAO;

/**
 * 提示词持久层实现类
 *
 * @author icanci
 * @since 1.0 Created in 2025/11/12 08:39
 */
@Service("promptDAO")
public class PromptDAOImpl extends AbstractBaseDAO<PromptDO> implements PromptDAO {
    @Override
    protected Class<PromptDO> getEntityClass() {
        return PromptDO.class;
    }

    @Override
    protected String getCollectionName() {
        return MongoDocuments.FIRMAMENT_PROMPT;
    }

    /**
     * 根据提示词ID查询提示词
     *
     * @param promptId 提示词ID
     * @return 提示词
     */
    @Override
    public PromptDO queryByPromptId(String promptId) {
        Query query = new Query(Criteria.where("promptId").is(promptId).and("deleted").is(false));
        return mongoTemplate.findOne(query, getEntityClass(), getCollectionName());
    }

    /**
     * 根据会员ID查询提示词
     *
     * @param memberId 会员ID
     * @return 提示词
     */
    @Override
    public List<PromptDO> queryAllByMemberId(String memberId) {
        Query query = new Query(Criteria.where("memberId").is(memberId).and("deleted").is(false));
        return mongoTemplate.find(query, getEntityClass(), getCollectionName());
    }

    /**
     * 根据会员ID和名称查询提示词
     *
     * @param memberId 会员ID
     * @param name     提示词名称
     * @return 提示词
     */
    @Override
    public PageList<PromptDO> pageQueryByNameAndMemberId(String memberId, String name, Integer page, Integer pageSize) {
        Query query = new Query(Criteria.where("memberId").is(memberId).and("deleted").is(false));
        // name 迷糊查询
        if (StringUtils.isNotBlank(name)) {
            query.addCriteria(Criteria.where("name").regex("^.*" + name + ".*$", "i"));
        }
        return pageQuery(query, getEntityClass(), pageSize, page, getCollectionName());
    }

    /**
     * 根据名称查询提示词
     *
     * @param name     提示词名称
     * @param memberId 会员ID
     * @return 提示词
     */
    @Override
    public PromptDO queryByName(String name, String memberId) {
        Query query = new Query(Criteria.where("name").is(name).and("memberId").is(memberId).and("deleted").is(false));
        return mongoTemplate.findOne(query, getEntityClass(), getCollectionName());
    }
}
