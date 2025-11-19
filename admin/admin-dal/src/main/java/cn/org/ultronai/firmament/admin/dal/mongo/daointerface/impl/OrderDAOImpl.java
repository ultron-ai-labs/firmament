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
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.OrderDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.OrderDO;
import cn.org.ultronai.firmament.admin.dal.mongo.mongo.AbstractBaseDAO;

/**
 * @author kai
 * @since 1.0 Created in 2025/11/06 20:05
 */
@Repository
public class OrderDAOImpl extends AbstractBaseDAO<OrderDO> implements OrderDAO {

    @Override
    protected Class<OrderDO> getEntityClass() {
        return OrderDO.class;
    }

    @Override
    protected String getCollectionName() {
        return MongoDocuments.FIRMAMENT_ORDER;
    }

    /**
     * 根据策略ID查询订单
     *
     * @param strategyId 策略ID
     * @return 订单列表
     */
    @Override
    public List<OrderDO> queryByStrategyId(String strategyId) {
        Query query = new Query(Criteria.where("strategyId").is(strategyId).and("deleted").is(false));
        return mongoTemplate.find(query, getEntityClass(), getCollectionName());
    }

    /**
     * 根据策略ID查询订单
     *
     * @param strategyId     策略ID
     * @param cryptocurrency cryptocurrency
     * @param state state
     * @return 订单列表
     */
    @Override
    public OrderDO queryByStrategyId(String strategyId, String cryptocurrency, int state) {
        Query query = new Query(Criteria.where("strategyId").is(strategyId).and("tradeCrypto").is(cryptocurrency).and("status").is(state).and("deleted").is(false));
        return mongoTemplate.findOne(query, getEntityClass(), getCollectionName());
    }

    @Override
    public PageList<OrderDO> queryPageByStrategyId(String strategyId, String tradeCrypto, String longOrShort, String closeStatus, int pageNum, int pageSize) {
        Query query = new Query(Criteria.where("strategyId").is(strategyId).and("deleted").is(false));
        if (StringUtils.isNotBlank(tradeCrypto)) {
            query.addCriteria(Criteria.where("tradeCrypto").is(tradeCrypto));
        }
        if (StringUtils.isNotBlank(longOrShort)) {
            query.addCriteria(Criteria.where("longOrShort").is(longOrShort));
        }
        if (StringUtils.isNotBlank(closeStatus)) {
            query.addCriteria(Criteria.where("status").is(Integer.parseInt(closeStatus)));
        }
        // 根据创建时间排序
        query.with(Sort.by(Sort.Direction.DESC, "createTime"));
        return pageQuery(query, getEntityClass(), pageSize, pageNum, getCollectionName());
    }

    /**
     * 根据订单ID查询订单
     *
     * @param orderSerialNo 订单ID
     * @return 订单
     */
    @Override
    public OrderDO queryByOrderId(String orderSerialNo) {
        Query query = new Query(Criteria.where("orderSerialNo").is(orderSerialNo).and("deleted").is(false));
        return mongoTemplate.findOne(query, getEntityClass(), getCollectionName());
    }

}