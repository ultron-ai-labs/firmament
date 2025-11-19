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
package cn.org.ultronai.firmament.aichat.deepseek;

import java.util.ArrayList;
import java.util.List;

import cn.org.ultronai.firmament.aichat.AiChatService;
import cn.org.ultronai.firmament.aichat.common.ChatMessage;
import cn.org.ultronai.firmament.commonapi.http.Client;
import cn.org.ultronai.firmament.commonapi.http.HttpClientImpl;

/**
 * @author icanci
 * @since 1.0 Created in 2025/10/20 22:13
 */
public abstract class DeepSeekChatService implements AiChatService {
    protected static final Client CLIENT = HttpClientImpl.getInstance();

    protected List<ChatMessage> buildTestMessage() {
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system", "你是一个数学老师！"));
        messages.add(new ChatMessage("user", "请帮我计算1+1等于几？"));
        return messages;
    }

    protected List<ChatMessage> buildChatMessage(String systemPrompt, String userPrompt) {
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system", systemPrompt));
        messages.add(new ChatMessage("user", userPrompt));
        return messages;
    }
}
