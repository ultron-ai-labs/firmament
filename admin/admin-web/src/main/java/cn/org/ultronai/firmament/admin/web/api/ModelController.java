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
package cn.org.ultronai.firmament.admin.web.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;

import cn.org.ultronai.firmament.admin.biz.model.aimodel.ModelAddReq;
import cn.org.ultronai.firmament.admin.biz.model.aimodel.ModelReq;
import cn.org.ultronai.firmament.admin.biz.service.ModelService;
import cn.org.ultronai.firmament.admin.dal.common.R;

/**
 * 模型商户管理
 * 
 * @author kai
 * @since 1.0 Created in 2025/11/05 16:45
 */
@RestController
@RequestMapping("/api/model")
public class ModelController {

    @Resource
    private ModelService modelService;

    @PostMapping("/testConnection")
    public R testConnection(@RequestHeader String accessToken, @RequestBody ModelAddReq modelAddReq) {
        boolean result = modelService.testModel(accessToken, modelAddReq);
        return R.builder().data("result", result).build();
    }

    @PostMapping("/addModel")
    public R addModel(@RequestHeader String accessToken, @RequestBody ModelAddReq modelAddReq) {
        boolean result = modelService.addModel(accessToken, modelAddReq);
        return R.builder().data("result", result).build();
    }

    @PostMapping("/updateModel")
    public R updateModel(@RequestHeader String accessToken, @RequestBody ModelAddReq modelAddReq) {
        boolean result = modelService.updateModel(accessToken, modelAddReq);
        return R.builder().data("result", result).build();
    }

    /**
     * 获取所有模型数据
     *
     * @return 获取所有模型数据
     */
    @PostMapping("/currentModels")
    public R currentModels(@RequestHeader String accessToken, @RequestBody ModelReq req) {
        return R.builderOk().data(modelService.currentModels(accessToken, req)).build();
    }

    /**
     * 获取所有模型数据
     *
     * @return 获取所有模型数据
     */
    @PostMapping("/currentAllModels")
    public R currentAllModels(@RequestHeader String accessToken) {
        return R.builderOk().data(modelService.currentAllModels(accessToken)).build();
    }
}