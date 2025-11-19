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
package cn.org.ultronai.firmament.admin.biz.model.account;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/05 17:51
 */
@Data
public class AccountUserInfoResp implements Serializable {
    private String       memberId;
    private String       username;
    private Set<String>  permissions;
    private String       email;
    private String       phone;
    private String       areaCode;
    private final String avatar = "https://gcore.jsdelivr.net/gh/zxwk1998/image/avatar/avatar_1.png";
}
