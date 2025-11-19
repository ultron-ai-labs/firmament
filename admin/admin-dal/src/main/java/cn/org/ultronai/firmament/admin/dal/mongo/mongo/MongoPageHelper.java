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
package cn.org.ultronai.firmament.admin.dal.mongo.mongo;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.google.common.collect.Lists;

import cn.org.ultronai.firmament.admin.dal.common.PageList;
import cn.org.ultronai.firmament.admin.dal.common.Paginator;

/**
 * 分页插件
 * 
 * @author icanci(1205068)
 * @version Id: MongoPageHelper, v 0.1 2022/10/24 16:44 icanci Exp $
 */
public class MongoPageHelper {
    @Resource
    protected MongoTemplate    mongoTemplate;

    public static final int    FIRST_PAGE_NUM = 1;

    public static final String ID             = "_id";

    /**
     * 分页查询，直接返回集合类型的结果.
     * 
     * @param query 查询
     * @param entityClass class
     * @param pageSize pageSize
     * @param pageNum pageNum
     * @param collectionName collectionName
     * @param <T> T
     * @return PageResult
     */
    public <T> PageList<T> pageQuery(Query query, Class<T> entityClass, Integer pageSize, Integer pageNum, String collectionName) {
        return pageQuery(query, entityClass, Function.identity(), pageSize, pageNum, null, collectionName);
    }

    /**
     * 分页查询，不考虑条件分页，直接使用skip-limit来分页.
     *
     * @param query 查询
     * @param entityClass class
     * @param pageSize pageSize
     * @param pageNum pageNum
     * @param collectionName collectionName
     * @param <T> T
     * @param <R> R
     * @param mapper mapper
     * @return PageResult
     */
    public <T, R> PageList<R> pageQuery(Query query, Class<T> entityClass, Function<T, R> mapper, Integer pageSize, Integer pageNum, String collectionName) {
        return pageQuery(query, entityClass, mapper, pageSize, pageNum, null, collectionName);
    }

    /**
     * 分页查询.
     *
     * @param query Mongo Query对象，构造你自己的查询条件
     * @param entityClass Mongo collection定义的entity class，用来确定查询哪个集合
     * @param mapper 映射器，从db查出来的list的元素类型是entityClass, 如果你想要转换成另一个对象，比如去掉敏感字段等，可以使用mapper来决定如何转换
     * @param pageSize 分页的大小
     * @param pageNum 当前页
     * @param lastId 条件分页参数, 区别于skip-limit，采用find(_id>lastId).limit分页
     * @param <T> collection定义的class类型
     * @param <R> 最终返回时，展现给页面时的一条记录的类型
     * @return PageResult，一个封装page信息的对象
     */
    public <T, R> PageList<R> pageQuery(Query query, Class<T> entityClass, Function<T, R> mapper, Integer pageSize, Integer pageNum, String lastId, String collectionName) {
        //分页逻辑
        long total = mongoTemplate.count(query, entityClass, collectionName);

        final int pages = (int) Math.ceil(total / (double) pageSize);

        if (pageNum <= 0 || pageNum > pages) {
            pageNum = FIRST_PAGE_NUM;
        }

        final Criteria criteria = new Criteria();

        if (StringUtils.isNotBlank(lastId)) {
            if (pageNum != FIRST_PAGE_NUM) {
                criteria.and(ID).gt(new ObjectId(lastId));
            }
            query.limit(pageSize);
        } else {
            int skip = pageSize * (pageNum - 1);
            query.skip(skip).limit(pageSize);
        }

        final List<T> entityList = mongoTemplate.find(query.addCriteria(criteria).with(Sort.by(Lists.newArrayList(new Order(Sort.Direction.ASC, ID)))), entityClass,
            collectionName);

        final PageList<R> pageResult = new PageList<>();
        Paginator paginator = pageResult.getPaginator();
        paginator.setTotalCount((int) total);
        paginator.setPageSize(pageSize);
        paginator.setCurrentPage(pageNum);
        pageResult.setData(entityList.stream().map(mapper).collect(Collectors.toList()));
        return pageResult;
    }
}