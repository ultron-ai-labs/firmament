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

import java.util.Date;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2023/08/19 17:52
 */
@Data
public class BaseDO {
    /** mongodb id */
    @Id
    private String  id;
    /** 雪花算法随机UUID  */
    private String  uuid;
    /** 用户Id  */
    private String  memberId;
    /** 描述  */
    private String  desc;
    /** 创建时间 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    createTime;
    /** 更新时间  */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    updateTime;
    /** 状态 false 有效，true 无效 */
    private boolean deleted;
    /** 环境 */
    private String  env;
}
