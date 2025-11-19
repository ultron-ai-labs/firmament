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

import lombok.Data;

/**
 * 日志
 * @author icanci
 * @since 1.0 Created in 2023/09/10 14:15
 */
@Data
public class LogDO extends BaseDO {
    /** 日志模块 backtestStrategy [回测]、realTimeStrategy[实时]、alertMessage[消息通知] */
    private String module;
    /** 记录ID */
    private String targetId;
    /** 操作类型 根据不同的module 设计不同的操作类型 */
    private String operatorType;
    /** 日志操作内容 */
    private String content;
}
