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
package cn.org.ultronai.firmament.admin.biz.service;

import java.util.List;

import cn.org.ultronai.firmament.admin.biz.model.aimodel.ModelAddReq;
import cn.org.ultronai.firmament.admin.biz.model.aimodel.ModelPageListResp;
import cn.org.ultronai.firmament.admin.biz.model.aimodel.ModelReq;
import cn.org.ultronai.firmament.admin.biz.model.aimodel.ModelResp;

/**
 * 模型商户服务
 * 
 * @author kai
 * @since 1.0 Created in 2025/11/05 16:35
 */
public interface ModelService {
    /**
     * 测试模型
     *
     * @param accessToken accessToken
     * @param modelAddReq modelAddReq
     * @return true/false
     */
    boolean testModel(String accessToken, ModelAddReq modelAddReq);

    /**
     * 添加模型
     *
     * @param accessToken accessToken
     * @param modelAddReq modelAddReq
     * @return modelAddResp
     */
    boolean addModel(String accessToken, ModelAddReq modelAddReq);

    /**
     * 添加模型
     *
     * @param accessToken accessToken
     * @param modelAddReq modelAddReq
     * @return modelAddResp
     */
    boolean updateModel(String accessToken, ModelAddReq modelAddReq);

    /**
     * 获取所有模型数据
     *
     * @param accessToken accessToken
     * @param req req
     * @return modelAddResp
     */
    ModelPageListResp currentModels(String accessToken, ModelReq req);

    /**
     * 获取所有模型数据
     *
     * @param accessToken accessToken
     * @return modelAddResp
     */
    List<ModelResp> currentAllModels(String accessToken);
}