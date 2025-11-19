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
 *
 * ------------------------------------------------------------------------
 * Coding Power by icanci.
 * ------------------------------------------------------------------------
 */
package cn.org.ultronai.firmament.aichat.deepseek.model;

import java.io.Serializable;
import java.util.List;

import cn.org.ultronai.firmament.aichat.common.ChatChoice;
import cn.org.ultronai.firmament.aichat.common.ChatUsage;
import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/10/29 14:02
 */
@Data
@SuppressWarnings("all")
public class DeepSeekChatResponse implements Serializable {
    private String           id;
    private String           object;
    private long             created;
    private String           model;
    private List<ChatChoice> choices;
    private ChatUsage        usage;
    private String           system_fingerprint;
}
