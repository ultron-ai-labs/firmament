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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

/**
 * @author icanci
 * @since 1.0 Created in 2022/11/14 22:14
 */
@SuppressWarnings("all")
public class HttpClientImpl extends AbstractRetryClient {

    private static final Logger   logger           = LoggerFactory.getLogger(HttpClientImpl.class);

    private static final long     DEFAULT_TIMEOUT  = 3;
    private static final TimeUnit DEFAULT_TIMEUNIT = TimeUnit.SECONDS;

    public static Client getInstance() {
        return HttpClientImplHolder.CLIENT_IMPL;
    }

    private static final class HttpClientImplHolder {
        private static final HttpClientImpl CLIENT_IMPL = new HttpClientImpl();
    }

    /**
     * Do execute v.
     *
     * @param request the request
     * @param clazz   the clazz
     * @return the v
     */
    @Override
    protected <V> V doExecute(RpcRequest request, Class<V> clazz) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<V> task = new FutureTask<V>(new HttpCallRunner<V>(request, clazz));
        HTTP_POOL.execute(task);
        return task.get(request.getReadTimeOut(), request.getTimeUnit());
    }

    /**
     * 请求执行器
     *
     * @param <V> 泛型
     */
    private static class HttpCallRunner<V> implements Callable<V> {
        private final RpcRequest request;
        private final Class<V>   clazz;

        public HttpCallRunner(RpcRequest request, Class<V> clazz) {
            this.request = request;
            this.clazz = clazz;
        }

        @Override
        public V call() throws Exception {
            switch (request.getMethod()) {
                case GET:
                    return doGet(request, clazz);
                case POST:
                    return doPost(request, clazz);
                default:
                    throw new IllegalAccessException("Un Support Http Method:" + request.getMethod());
            }
        }

        private <V> V doGet(RpcRequest request, Class<V> clazz) {
            Map body = new HashMap();
            if (request.getBody() instanceof Map) {
                body = (Map) request.getBody();
            }
            String getBody = HttpUtil.createGet(request.getUrl()) // 
                .addHeaders(request.getHeaders())//
                .form(body)//
                .execute().body();
            logger.info("[{}][HttpCallRunner][doGet] request:{},resp:{}", Thread.currentThread().getName(), JSONUtil.toJsonStr(request), getBody);
            return JSONUtil.toBean(getBody, clazz);
        }

        private <V> V doPost(RpcRequest request, Class<V> clazz) {
            String postBody = HttpUtil.createPost(request.getUrl()) // 
                .addHeaders(request.getHeaders())//
                .body(JSONUtil.toJsonStr(request.getBody()))//
                .execute().body();
            logger.info("[{}][HttpCallRunner][doPost] request:{},resp:{}", Thread.currentThread().getName(), JSONUtil.toJsonStr(request), postBody);
            return JSONUtil.toBean(postBody, clazz);
        }
    }
}
