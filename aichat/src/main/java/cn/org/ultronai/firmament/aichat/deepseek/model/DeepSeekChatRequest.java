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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import cn.org.ultronai.firmament.aichat.common.ChatMessage;
import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/10/29 14:27
 */
@Data
public class DeepSeekChatRequest {
    private List<ChatMessage> messages;
    private String            model;
    @JsonProperty("frequency_penalty")
    private int               frequencyPenalty;
    @JsonProperty("max_tokens")
    private int               maxTokens   = 4096;
    @JsonProperty("presence_penalty")
    private int               presencePenalty;
    @JsonProperty("response_format")
    private ResponseFormat    responseFormat;
    private String            stop;
    private boolean           stream      = false;
    @JsonProperty("stream_options")
    private String            streamOptions;
    private int               temperature = 1;
    @JsonProperty("top_p")
    private int               topP        = 1;
    private String            tools;
    @JsonProperty("tool_choice")
    private String            toolChoice  = "none";
    private boolean           logprobs;
    @JsonProperty("top_logprobs")
    private String            topLogprobs;

    @Data
    public static class ResponseFormat {
        private String type = "json_object";
    }
}
