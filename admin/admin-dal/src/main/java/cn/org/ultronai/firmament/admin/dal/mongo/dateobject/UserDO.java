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
package cn.org.ultronai.firmament.admin.dal.mongo.dateobject;

import java.util.Set;

import lombok.Data;

/**
 * @version Id: UserDO, v 2025/11/05 13:17 kai Exp $
 * @author: kai
 * @Description: UserDO
 */
@Data
public class UserDO extends BaseDO {
    /**
     * 用户名称
     */
    private String      userName;
    /**
     * 密码（MD5加密）
     */
    private String      password;
    /**
     * 邮箱
     */
    private String      email;
    /**
     * 区号
     */
    private String      areaCode;
    /**
     * 手机号
     */
    private String      phone;
    /**
     * 权限代码 管理员：admin；普通用户：editor
     * 管理员账户由管理员添加
     */
    private Set<String> permissions;
    /**
     * 无状态访问token 创建用户的时候生成
     */
    private String      accessToken;
}