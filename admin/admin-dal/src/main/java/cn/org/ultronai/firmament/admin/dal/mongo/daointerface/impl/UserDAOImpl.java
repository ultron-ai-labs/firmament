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

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import cn.org.ultronai.firmament.admin.dal.mongo.MongoDocuments;
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.UserDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.UserDO;
import cn.org.ultronai.firmament.admin.dal.mongo.mongo.AbstractBaseDAO;

/**
 * @author kai
 * @since 1.0 Created in 2025/11/05 14:05
 */
@Repository
public class UserDAOImpl extends AbstractBaseDAO<UserDO> implements UserDAO {

    @Override
    protected Class<UserDO> getEntityClass() {
        return UserDO.class;
    }

    @Override
    protected String getCollectionName() {
        return MongoDocuments.FIRMAMENT_USER;
    }

    @Override
    public UserDO queryByUserName(String userName) {
        Query query = new Query(Criteria.where("userName").is(userName).and("deleted").is(false));
        return mongoTemplate.findOne(query, getEntityClass(), getCollectionName());
    }

    @Override
    public UserDO queryByPhone(String phone) {
        Query query = new Query(Criteria.where("phone").is(phone).and("deleted").is(false));
        return mongoTemplate.findOne(query, getEntityClass(), getCollectionName());
    }

    @Override
    public UserDO queryByEmail(String email) {
        Query query = new Query(Criteria.where("email").is(email).and("deleted").is(false));
        return mongoTemplate.findOne(query, getEntityClass(), getCollectionName());
    }

    /**
     * 根据accessToken查询用户
     *
     * @param accessToken accessToken
     * @return 用户信息
     */
    @Override
    public UserDO queryByAccessToken(String accessToken) {
        Query query = new Query(Criteria.where("accessToken").is(accessToken).and("deleted").is(false));
        return mongoTemplate.findOne(query, getEntityClass(), getCollectionName());
    }
}