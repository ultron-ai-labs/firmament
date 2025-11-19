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

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.org.ultronai.firmament.admin.biz.model.ad.AdDetail;
import cn.org.ultronai.firmament.admin.biz.service.AdService;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/05 09:59
 */
@Service("adService")
public class AdServiceImpl implements AdService {
    /**
     * 获取推广列表
     *
     * @return 推广列表
     */
    @Override
    public List<AdDetail> list() {
        // TODO: 2025/11/05 获取推广列表 从数据获取
        List<AdDetail> list = Lists.newArrayList();
        AdDetail adDetail = new AdDetail();
        adDetail.setTitle("苍穹AI量化多模型，即将上线...");
        adDetail.setUrl("");
        list.add(adDetail);
        return list;
    }
}
