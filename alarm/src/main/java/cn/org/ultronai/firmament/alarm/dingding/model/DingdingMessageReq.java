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
package cn.org.ultronai.firmament.alarm.dingding.model;

import lombok.Data;

/**
 * 钉钉消息模型
 */
@Data
public class DingdingMessageReq {

    // 消息类型，可为 text、link、markdown、actionCard、feedCard
    private String            msgtype;

    private String            msgUuid;

    private DingdingTextMessage text;

    private DingdingLinkMessage link;

    private DingdingMarkdownMessage markdown;

    private DingdingActionCardMessage actionCard;

    private DingdingFeedCardMessage feedCard;

    private DingdingAtInfo at;
}
