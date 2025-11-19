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
package cn.org.ultronai.firmament.admin.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 应用入口
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/04 14:25
 */
@EnableScheduling
@EnableAsync
@ComponentScan(basePackages = { "cn.org.ultronai.firmament" })
@SpringBootApplication
public class FirmamentApplication {
    public static void main(String[] args) {
        System.setProperty("proxyHost", "127.0.0.1");
        System.setProperty("proxyPort", "7890");
        SpringApplication.run(FirmamentApplication.class, args);
    }
}
