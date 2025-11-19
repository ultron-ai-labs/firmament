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
package cn.org.ultronai.firmament.admin.dal.mongo.daointerface;

import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.UserDO;

/**
 * @author kai
 * @since 1.0 Created in 2025/11/05 14:00
 */
public interface UserDAO extends BaseDAO<UserDO> {

    // 可以在这里定义用户特有的数据访问方法
    // 例如根据用户名查找用户等

    /**
     * 根据用户名查询用户
     * 
     * @param userName 用户名
     * @return 用户信息
     */
    UserDO queryByUserName(String userName);

    /**
     * 根据手机号查询用户
     * 
     * @param phone 手机号
     * @return 用户信息
     */
    UserDO queryByPhone(String phone);

    /**
     * 根据邮箱查询用户
     * 
     * @param email 邮箱
     * @return 用户信息
     */
    UserDO queryByEmail(String email);

    /**
     * 根据accessToken查询用户
     *
     * @param accessToken accessToken
     * @return 用户信息
     */
    UserDO queryByAccessToken(String accessToken);

}