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
package cn.org.ultronai.firmament.aichat;

import cn.org.ultronai.firmament.aichat.trade.ChatWrapper;

/**
 * @author icanci
 * @since 1.0 Created in 2025/10/20 22:13
 */
public interface AiChatService {
    /**
     * 测试模型
     * 
     * @param modelAddress  modelAddress
     * @param modelKey modelKey
     * @param category  category
     * @return ture/false
     */
    boolean testModel(String modelAddress, String modelKey, String category);

    /**
     * 聊天
     * 
     * @param modelAddress modelAddress
     * @param modelKey modelKey
     * @param category category
     * @param systemPrompt systemPrompt
     * @param userPrompt userPrompt
     * @return DeepSeekChatResponse.DeepSeekChatChoice
     */
    ChatWrapper chat(String modelAddress, String modelKey, String category, String systemPrompt, String userPrompt);
}
