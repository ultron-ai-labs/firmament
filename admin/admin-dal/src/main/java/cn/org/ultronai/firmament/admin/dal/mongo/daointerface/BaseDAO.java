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
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.BaseDO;

/**
 * @author icanci
 * @since 1.0 Created in 2023/08/19 17:53
 */
public interface BaseDAO<T extends BaseDO> {

    /**
     * 插入文档一条记录
     *
     * @param t t
     */
    void insert(T t);

    /**
     * 批量插入文档多条记录
     *
     * @param list list
     */
    void batchInsert(List<T> list);

    /**
     * 更新文档一条记录
     *
     * @param t t
     */
    void update(T t);

    /**
     * 查询文档所有记录
     *
     * @return 返回查询的结果
     */
    List<T> queryAll();

    /**
     * 查询文档所有记录
     *
     * @param t 请求参数
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return 返回查询的结果
     */
    PageList<T> pageQuery(T t, int pageNum, int pageSize);

    /**
     * 根据 _id 查询一条信息
     *
     * @param _id _id
     * @return 返回查询的结果
     */
    T queryOneById(String _id);

    /**
     * 根据 _id 删除一条记录（软删除）
     *
     * @param _id _id
     */
    void delete(String _id);
}
