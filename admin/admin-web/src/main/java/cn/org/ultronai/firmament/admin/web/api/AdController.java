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

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.org.ultronai.firmament.admin.biz.service.AdService;
import cn.org.ultronai.firmament.admin.dal.common.R;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/05 09:55
 */
@RestController
@RequestMapping("/api/ad")
public class AdController {
    @Resource
    private AdService adService;

    @GetMapping("/list")
    public R list() {
        return R.builderOk().data("data", adService.list()).build();
    }
}
