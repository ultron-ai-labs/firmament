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
package cn.org.ultronai.firmament.exchange.okx.model;

import java.util.List;

import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/10/29 13:06
 */
@Data
public class OkxResponse<T> {
    private String  code;
    private String  msg;
    private List<T> data;
}
