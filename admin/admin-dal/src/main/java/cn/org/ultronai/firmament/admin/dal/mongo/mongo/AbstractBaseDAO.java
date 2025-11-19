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
package cn.org.ultronai.firmament.admin.dal.mongo.mongo;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import cn.org.ultronai.firmament.admin.dal.common.PageList;
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.BaseDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.BaseDO;
import cn.org.ultronai.firmament.admin.dal.utils.EnvUtils;
import cn.org.ultronai.firmament.admin.dal.utils.IdHolder;

/**
 * @author icanci
 * @since 1.0 Created in 2023/08/19 18:00
 */
public abstract class AbstractBaseDAO<T extends BaseDO> extends MongoPageHelper implements BaseDAO<T>, InitializingBean {

    @Resource
    protected MongoTemplate mongoTemplate;

    protected String        DEFAULT_ENV;

    @Override
    public void insert(T t) {
        // 处理插入数据
        doInsert(t);
        mongoTemplate.save(t, getCollectionName());
    }

    @Override
    public void update(T t) {
        t.setUpdateTime(new Date());
        mongoTemplate.save(t, getCollectionName());
    }

    @Override
    public void delete(String _id) {
        Query query = new Query(Criteria.where("id").is(_id));
        T entity = mongoTemplate.findOne(query, getEntityClass(), getCollectionName());
        if (entity != null) {
            entity.setDeleted(true);
            entity.setUpdateTime(new Date());
            mongoTemplate.save(entity, getCollectionName());
        }
    }

    @Override
    public List<T> queryAll() {
        Query query = new Query(Criteria.where("deleted").is(false));
        return mongoTemplate.find(query, getEntityClass(), getCollectionName());
    }

    @Override
    public PageList<T> pageQuery(T t, int pageNum, int pageSize) {
        Query query = new Query();
        // 这里可以根据传入的参数t构建查询条件
        // 目前只是简单地查询未删除的记录
        query.addCriteria(Criteria.where("deleted").is(false));
        return pageQuery(query, getEntityClass(), pageSize, pageNum, getCollectionName());
    }

    @Override
    public T queryOneById(String _id) {
        Query query = new Query(Criteria.where("id").is(_id).and("deleted").is(false));
        return mongoTemplate.findOne(query, getEntityClass(), getCollectionName());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DEFAULT_ENV = EnvUtils.getEnv();
    }

    public void batchInsert(List<T> list) {
        // 处理插入数据
        list.forEach(this::doInsert);
        // 将大数据列表拆分为更小的批次（每批1000条记录）以避免内存溢出
        int batchSize = 1000;
        for (int i = 0; i < list.size(); i += batchSize) {
            int end = Math.min(i + batchSize, list.size());
            List<T> subList = list.subList(i, end);
            mongoTemplate.insert(subList, getCollectionName());
        }
    }

    protected void doInsert(T t) {
        t.setId(null);
        t.setDeleted(false);
        t.setCreateTime(new Date());
        t.setUpdateTime(new Date());
        t.setEnv(DEFAULT_ENV);
        t.setUuid(IdHolder.generateNoBySnowFlakeDefaultPrefix());
    }

    /**
     * 获取实体类类型
     * 子类需要实现此方法返回具体的实体类class
     *
     * @return 实体类class
     */
    protected abstract Class<T> getEntityClass();

    /**
     * 获取集合名称
     * 子类需要实现此方法返回对应的集合名称
     *
     * @return 集合名称
     */
    protected abstract String getCollectionName();
}