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
package cn.org.ultronai.firmament.admin.dal.common;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author icanci
 * @since 1.0 Created in 2023/08/19 17:51
 */
public class PageList<T> implements Serializable {
    /**  */
    private static final long serialVersionUID = 1L;

    /** 分页器 */
    private Paginator         paginator;

    /** 数据集 */
    private Collection<T>     data;

    /**
     * 默认构造函数
     */
    public PageList() {
        paginator = new Paginator();
    }

    /**
     * 构造函数
     *
     * @param data 数据集
     */
    public PageList(Collection<T> data) {
        this.data = data;
    }

    /**
     * 构造函数
     *
     * @param data 数据集
     * @param paginator 分页器
     */
    public PageList(Collection<T> data, Paginator paginator) {
        this.data = data;
        this.paginator = (paginator == null) ? new Paginator() : paginator;
    }

    /**
     * 获取分页器
     *
     * @return 分页器
     */
    public Paginator getPaginator() {
        return paginator;
    }

    /**
     * 设置分页器
     *
     * @param paginator 分页器
     */
    public void setPaginator(Paginator paginator) {
        if (paginator != null) {
            this.paginator = paginator;
        }
    }

    /**
     * 获取数据集
     *
     * @return 数据集
     */
    public Collection<T> getData() {
        return data;
    }

    /**
     * 集数据
     *
     * @param data 数据
     */
    public void setData(Collection<T> data) {
        this.data = data;
    }
}
