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

import org.apache.commons.lang3.RandomUtils;

import cn.hutool.core.lang.Snowflake;

/**
 * @author icanci
 * @since 1.0 Created in 2023/08/21 13:50
 */
public class IdHolder {
    /** 雪花序列号生成算法 */
    private static final Snowflake SNOW_FLAKE     = new Snowflake(RandomUtils.nextInt(1, 9), RandomUtils.nextInt(1, 9));

    private static final String    DEFAULT_PREFIX = "FT";

    public static String generateBySnowFlake(String prefix) {
        return prefix + SNOW_FLAKE.nextId();
    }

    /**
     * 通过雪花算法生成唯一id，默认 DAMC
     *
     * @return id
     */
    public static String generateNoBySnowFlakeDefaultPrefix() {
        return generateBySnowFlake(DEFAULT_PREFIX);
    }

}
