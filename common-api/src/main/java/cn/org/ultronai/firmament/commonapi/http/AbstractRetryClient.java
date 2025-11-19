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

import java.util.concurrent.*;

/**
 * 自动重试客户端
 *
 * @author icanci
 * @since 1.0 Created in 2022/11/14 22:16
 */
public abstract class AbstractRetryClient implements Client {

    protected static final ThreadPoolExecutor HTTP_POOL = new ThreadPoolExecutor(40, //
        120, //
        60L, //
        TimeUnit.SECONDS, //
        new LinkedBlockingQueue<>(2000), //
        runnable -> new Thread(runnable, "AbstractRetryClient Pool-" + runnable.hashCode()), //
        (r, executor) -> {
            r.run();
        });

    @Override
    public <V> V call(RpcRequest request, Class<V> clazz) throws RemoteException {
        return retry(request, clazz, 0, request.getRetry());
    }

    private <V> V retry(RpcRequest request, Class<V> clazz, int retryCount, int retry) throws RemoteException {
        try {
            return doExecute(request, clazz);
        } catch (RemoteException | ExecutionException | InterruptedException e) {
            throw new RemoteException(e);
        } catch (TimeoutException e) {
            while (retryCount < retry) {
                retryCount++;
                return retry(request, clazz, retryCount, retry);
            }
            throw new RemoteException(e.getMessage(), e);
        }
    }

    protected abstract <V> V doExecute(RpcRequest request, Class<V> clazz) throws ExecutionException, InterruptedException, TimeoutException;

}
