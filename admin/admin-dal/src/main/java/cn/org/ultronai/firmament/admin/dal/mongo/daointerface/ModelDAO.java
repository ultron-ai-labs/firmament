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

import java.util.List;

import cn.org.ultronai.firmament.admin.dal.common.PageList;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.ModelDO;

/**
 * @author kai
 * @since 1.0 Created in 2025/11/05 16:15
 */
public interface ModelDAO extends BaseDAO<ModelDO> {

    /**
     * 根据用户ID查询模型商户信息列表
     * 
     * @param memberId 用户ID
     * @return 模型商户信息列表
     */
    List<ModelDO> queryByMemberId(String memberId);

    /**
     * 根据用户ID和启用状态查询模型商户信息列表
     * 
     * @param memberId 用户ID
     * @param enable 是否启用 0 否 1 是
     * @return 模型商户信息列表
     */
    List<ModelDO> queryByMemberIdAndEnable(String memberId, Integer enable);

    /**
     * 根据用户ID和模型名称查询模型商户信息
     * 
     * @param memberId 用户ID
     * @param name 模型名称
     * @param page 页码
     * @param pageSize 每页大小
     * @return 模型商户信息列表
     */
    PageList<ModelDO> queryByMemberIdAndName(String memberId, String name, Integer page, Integer pageSize);

    /**
     * 根据模型ID查询模型商户信息
     *
     * @param memberId memberId
     * @param modelId 模型ID
     * @return 模型商户信息
     */
    ModelDO queryByModelId(String memberId, String modelId);
}