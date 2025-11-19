/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.org.ultronai.firmament.admin.dal.mongo.daointerface.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import cn.org.ultronai.firmament.admin.dal.common.PageList;
import cn.org.ultronai.firmament.admin.dal.mongo.MongoDocuments;
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.ModelDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.ModelDO;
import cn.org.ultronai.firmament.admin.dal.mongo.mongo.AbstractBaseDAO;

/**
 * @author kai
 * @since 1.0 Created in 2025/11/05 16:20
 */
@Repository
public class ModelDAOImpl extends AbstractBaseDAO<ModelDO> implements ModelDAO {

    @Override
    protected Class<ModelDO> getEntityClass() {
        return ModelDO.class;
    }

    @Override
    protected String getCollectionName() {
        return MongoDocuments.FIRMAMENT_MODEL;
    }

    @Override
    public List<ModelDO> queryByMemberId(String memberId) {
        Query query = new Query(Criteria.where("memberId").is(memberId).and("deleted").is(false));
        return mongoTemplate.find(query, getEntityClass(), getCollectionName());
    }

    @Override
    public List<ModelDO> queryByMemberIdAndEnable(String memberId, Integer enable) {
        Query query = new Query(Criteria.where("memberId").is(memberId).and("enable").is(enable).and("deleted").is(false));
        return mongoTemplate.find(query, getEntityClass(), getCollectionName());
    }

    @Override
    public PageList<ModelDO> queryByMemberIdAndName(String memberId, String name, Integer page, Integer pageSize) {
        // name 模糊查询
        Criteria criteria = Criteria.where("deleted").is(false);

        if (StringUtils.isNotBlank(name)) {
            // 不分区大小写查询，其中操作符"i"：表示不分区大小写
            criteria.and("name").regex("^.*" + name + ".*$", "i");
        }
        criteria.and("memberId").is(memberId);

        Query query = new Query(criteria);
        query.with(Sort.by(Sort.Direction.DESC, "updateTime"));

        return pageQuery(query, getEntityClass(), pageSize, page, getCollectionName());
    }

    /**
     * 根据模型ID查询模型商户信息
     *
     * @param memberId memberId
     * @param modelId  模型ID
     * @return 模型商户信息
     */
    @Override
    public ModelDO queryByModelId(String memberId, String modelId) {
        Query query = new Query(Criteria.where("memberId").is(memberId).and("modelId").is(modelId).and("deleted").is(false));
        return mongoTemplate.findOne(query, getEntityClass(), getCollectionName());
    }

}