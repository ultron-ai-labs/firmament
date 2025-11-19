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
package cn.org.ultronai.firmament.admin.dal.utils.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import cn.org.ultronai.firmament.admin.dal.utils.EnvUtils;
import cn.org.ultronai.firmament.admin.dal.utils.service.EnvService;

/**
 * @author icanci
 * @since 1.0 Created in 2022/11/12 08:29
 */
@Service("envService")
public class EnvServiceImpl implements EnvService, BeanPostProcessor {
    @Value("${env}")
    private String env;

    @Override
    public String getEnv() {
        return env;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        EnvUtils.setEnvService(this);
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
