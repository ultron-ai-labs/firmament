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
package cn.org.ultronai.firmament.commonapi.http;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.hutool.http.Method;

/**
 * @author icanci
 * @since 1.0 Created in 2022/11/14 22:14
 */
public interface Client {
    /**
     * 远程调用
     *
     * @param request http 请求
     * @param clazz   应答类型
     * @param <V>     应答类型
     * @return 应答数据
     * @throws RemoteException 远程调用异常
     */
    <V> V call(RpcRequest request, Class<V> clazz) throws RemoteException;

    class RpcRequest {
        /**
         * 请求路径
         */
        private String              url;
        /**
         * 请求对象
         */
        private Object              body;
        /**
         * 请求头
         */
        private Map<String, String> headers;
        /**
         * 执行的方法
         */
        private Method              method;
        /**
         * 请求超时时间
         */
        private long                readTimeOut;
        /**
         * 超时时间单位
         */
        private TimeUnit            timeUnit;
        /**
         * 重试次数
         */
        private int                 retry;

        public RpcRequest(String url, Object body, Map<String, String> headers, Method method, long readTimeOut, TimeUnit timeUnit, int retry) {
            this.url = url;
            this.body = body;
            this.headers = headers;
            this.method = method;
            this.readTimeOut = readTimeOut;
            this.timeUnit = timeUnit;
            this.retry = retry;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Object getBody() {
            return body;
        }

        public void setBody(Object body) {
            this.body = body;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public void setHeaders(Map<String, String> headers) {
            this.headers = headers;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public long getReadTimeOut() {
            return readTimeOut;
        }

        public void setReadTimeOut(long readTimeOut) {
            this.readTimeOut = readTimeOut;
        }

        public TimeUnit getTimeUnit() {
            return timeUnit;
        }

        public void setTimeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
        }

        public int getRetry() {
            return retry;
        }

        public void setRetry(int retry) {
            this.retry = retry;
        }
    }
}
