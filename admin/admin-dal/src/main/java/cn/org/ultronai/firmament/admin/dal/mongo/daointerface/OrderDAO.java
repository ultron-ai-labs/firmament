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
package cn.org.ultronai.firmament.admin.dal.mongo.daointerface;

import java.util.List;

import cn.org.ultronai.firmament.admin.dal.common.PageList;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.OrderDO;

/**
 * @author kai
 * @since 1.0 Created in 2025/11/06 20:00
 */
public interface OrderDAO extends BaseDAO<OrderDO> {
    /**
     * 根据策略ID查询订单
     *
     * @param strategyId 策略ID
     * @return 订单列表
     */
    List<OrderDO> queryByStrategyId(String strategyId);

    /**
     * 根据策略ID查询订单
     *
     * @param strategyId 策略ID
     * @return 订单列表
     */
    OrderDO queryByStrategyId(String strategyId, String cryptocurrency, int state);

    // 可以在这里定义订单特有的数据访问方法
    // 例如根据订单号查找订单等
    /**
     * 根据策略ID查询订单
     *
     * @param strategyId   策略ID
     * @param tradeCrypto tradeCrypto
     * @param longOrShort longOrShort
     * @param closeStatus closeStatus
     * @return 订单列表
     */
    PageList<OrderDO> queryPageByStrategyId(String strategyId, String tradeCrypto, String longOrShort, String closeStatus, int pageNum, int pageSize);

    /**
     * 根据订单ID查询订单
     *
     * @param orderSerialNo 订单ID
     * @return 订单
     */
    OrderDO queryByOrderId(String orderSerialNo);
}