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
package cn.org.ultronai.firmament.admin.biz.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.org.ultronai.firmament.admin.biz.model.aimodel.ModelAddReq;
import cn.org.ultronai.firmament.admin.biz.model.aimodel.ModelPageListResp;
import cn.org.ultronai.firmament.admin.biz.model.aimodel.ModelReq;
import cn.org.ultronai.firmament.admin.biz.model.aimodel.ModelResp;
import cn.org.ultronai.firmament.admin.biz.service.ModelService;
import cn.org.ultronai.firmament.admin.biz.utils.PrefixUtils;
import cn.org.ultronai.firmament.admin.dal.common.PageList;
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.ModelDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.ModelDO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.UserDO;
import cn.org.ultronai.firmament.aichat.AIModelTypeEnum;
import cn.org.ultronai.firmament.aichat.AiChatService;
import cn.org.ultronai.firmament.aichat.AiChatServiceProxy;

/**
 * 模型商户服务实现类
 * 
 * @author kai
 * @since 1.0 Created in 2025/11/05 16:40
 */
@Service("modelMerchantService")
public class ModelServiceImpl extends BaseService implements ModelService {
    @Resource
    private ModelDAO modelDAO;

    @Override
    public boolean testModel(String accessToken, ModelAddReq modelAddReq) {
        getUserDO(accessToken);
        AIModelTypeEnum aiModel = AIModelTypeEnum.getByModelType(modelAddReq.getModelType());
        if (aiModel == null) {
            throw new RuntimeException("模型类型错误");
        }
        AiChatService aiChat = AiChatServiceProxy.getAiChat(aiModel);
        return aiChat.testModel(modelAddReq.getApiUrl(), modelAddReq.getApiKey(), modelAddReq.getTestCategory());
    }

    @Override
    public boolean addModel(String accessToken, ModelAddReq modelAddReq) {
        // TODO log
        UserDO userDO = getUserDO(accessToken);
        ModelDO modelDO = new ModelDO();
        modelDO.setName(modelAddReq.getName());
        modelDO.setModelType(modelAddReq.getModelType());
        modelDO.setApiUrl(modelAddReq.getApiUrl());
        modelDO.setApiKey(modelAddReq.getApiKey());
        modelDO.setMemberId(userDO.getMemberId());
        modelDO.setTestCategory(modelAddReq.getTestCategory());
        modelDO.setModelId(PrefixUtils.genModelId(userDO.getMemberId()));
        modelDAO.insert(modelDO);
        return true;
    }

    /**
     * 添加模型
     *
     * @param accessToken accessToken
     * @param modelAddReq modelAddReq
     * @return modelAddResp
     */
    @Override
    public boolean updateModel(String accessToken, ModelAddReq modelAddReq) {
        // TODO log
        UserDO userDO = getUserDO(accessToken);
        ModelDO modelDO = modelDAO.queryByModelId(userDO.getMemberId(), modelAddReq.getModelId());
        if (modelDO == null) {
            throw new RuntimeException("模型不存在");
        }
        modelDO.setName(modelAddReq.getName());
        modelDO.setModelType(modelAddReq.getModelType());
        modelDO.setApiUrl(modelAddReq.getApiUrl());
        modelDO.setApiKey(modelAddReq.getApiKey());
        modelDO.setTestCategory(modelAddReq.getTestCategory());
        modelDAO.update(modelDO);
        return true;
    }

    /**
     * 获取所有模型数据
     *
     * @param accessToken accessToken
     * @param req         req
     * @return modelAddResp
     */
    @Override
    public ModelPageListResp currentModels(String accessToken, ModelReq req) {
        UserDO userDO = getUserDO(accessToken);
        PageList<ModelDO> pageList = modelDAO.queryByMemberIdAndName(userDO.getMemberId(), req.getName(), req.getPage(), req.getPageSize());
        return new ModelPageListResp(pageList.getPaginator().getTotalCount(), pageList.getPaginator().getCurrentPage(), pageList.getPaginator().getPageSize(),
            pageList.getData().stream().map(item -> {
                ModelResp modelResp = new ModelResp();
                modelResp.setName(item.getName());
                modelResp.setModelId(item.getModelId());
                modelResp.setModelType(item.getModelType());
                modelResp.setApiUrl(item.getApiUrl());
                modelResp.setApiKey(item.getApiKey());
                modelResp.setTestCategory(item.getTestCategory());
                modelResp.setCreateTime(item.getCreateTime());
                return modelResp;
            }).collect(Collectors.toList()));
    }

    /**
     * 获取所有模型数据
     *
     * @param accessToken accessToken
     * @return modelAddResp
     */
    @Override
    public List<ModelResp> currentAllModels(String accessToken) {
        UserDO userDO = getUserDO(accessToken);
        return modelDAO.queryByMemberId(userDO.getMemberId()).stream().map(item -> {
            ModelResp modelResp = new ModelResp();
            modelResp.setName(item.getName());
            modelResp.setModelId(item.getModelId());
            modelResp.setModelType(item.getModelType());
            modelResp.setApiUrl(item.getApiUrl());
            modelResp.setApiKey(item.getApiKey());
            modelResp.setTestCategory(item.getTestCategory());
            modelResp.setCreateTime(item.getCreateTime());
            return modelResp;
        }).collect(Collectors.toList());
    }
}