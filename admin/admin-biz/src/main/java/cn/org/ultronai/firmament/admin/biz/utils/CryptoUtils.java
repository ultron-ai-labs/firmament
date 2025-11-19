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
package cn.org.ultronai.firmament.admin.biz.utils;

import cn.hutool.crypto.SecureUtil;

/**
 * 加密工具
 * 
 * @author icanci(1205068)
 * @version Id: CryptoUtils, v 0.1 2025/11/5 17:43 icanci Exp $
 */
public class CryptoUtils {
    /**
     * md5
     * 
     * @param target target
     * @return String
     */
    public static String md5(String target) {
        return SecureUtil.md5(target);
    }
}