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

import java.util.List;

import lombok.Data;

/**
 * ActionCard消息模型
 */
@Data
public class DingdingActionCardMessage {
    
    private String title; // 消息标题
    
    private String text; // 消息内容（支持Markdown）
    
    private String btnOrientation; // 0-按钮竖直排列，1-按钮横向排列
    
    private String singleTitle; // 单个按钮标题
    
    private String singleUrl; // 单个按钮跳转链接
    
    private List<Button> btns; // 按钮列表
    
    /**
     * 按钮模型
     */
    @Data
    public static class Button {
        
        private String title; // 按钮标题
        
        private String actionUrl; // 按钮跳转链接
    }
}
