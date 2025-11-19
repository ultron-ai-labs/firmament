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
package cn.org.ultronai.firmament.admin.dal.utils;

import org.apache.commons.lang3.StringUtils;

import cn.org.ultronai.firmament.admin.dal.utils.service.EnvService;
import cn.org.ultronai.firmament.admin.dal.utils.service.impl.EnvServiceImpl;

/**
 * 环境标识
 *
 * @author icanci
 * @since 1.0 Created in 2022/11/12 08:26
 */
public class EnvUtils {

    private static final String DEFAULT_ENV = "test";

    private static EnvService   envService;

    private static String       currEnv;

    public static String getEnv() {
        if (StringUtils.isBlank(currEnv)) {
            String env = envService.getEnv();
            currEnv = StringUtils.isBlank(env) ? DEFAULT_ENV : env;
        }
        return currEnv;
    }

    public static void setEnvService(EnvServiceImpl envService) {
        EnvUtils.envService = envService;
    }
}
