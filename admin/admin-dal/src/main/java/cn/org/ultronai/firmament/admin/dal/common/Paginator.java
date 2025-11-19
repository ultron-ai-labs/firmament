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

/**
 * @author icanci
 * @since 1.0 Created in 2022/10/24 22:42
 */
public class Paginator implements Serializable {

    /**  */
    private static final long serialVersionUID  = 1L;

    /** 默认每页数据条目20 */
    private static final int  DEFAULT_PAGE_SIZE = 20;

    /** 数据总量 */
    private int               totalCount;

    /** 总页数 */
    private int               totalPage;

    /** 每页条目数 */
    private int               pageSize          = DEFAULT_PAGE_SIZE;

    /** 查询起始条目编号，默认从0开始 */
    private int               startIndex;

    /** 当前页页码 */
    private int               currentPage;

    /** 上一页页码 */
    private int               nextPage;

    /** 下一页页码 */
    private int               priviousPage;

    /**
     * 默认构造函数
     */
    protected Paginator() {
        super();
    }

    /**
     * 构造函数
     *
     * @param totalCount 数据总条目数
     * @param pageSize 每页条目数
     * @param currentPage 当前页码
     */
    protected Paginator(int totalCount, int pageSize, int currentPage) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

    /**
     * 获取数据总条目数
     *
     * @return 数据总条目数
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * 设置数据总条目数
     *
     * @param totalCount 数据总条目数
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 获取每页数据条目数
     *
     * @return 每页数据条目数
     */
    public int getPageSize() {
        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    /**
     * 设置每页条目数
     *
     * @param pageSize 每页条目数
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取当前页码
     *
     * @return 当前页面
     */
    public int getCurrentPage() {
        if (currentPage <= 0) {
            return 1;
        } else if (currentPage > getTotalPage()) {
            return getTotalPage();
        }
        return currentPage;
    }

    /**
     * 设置当前页码
     *
     * @param currentPage 当前页码
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * 获取总页数
     *
     * @return 总页数
     */
    public int getTotalPage() {
        if (totalCount > 0) {
            if (getPageSize() > 0) {
                totalPage = totalCount / getPageSize();
                if ((totalCount % getPageSize()) > 0) {
                    totalPage++;
                }
            } else {
                totalPage = 1;
            }
        } else {
            totalPage = 0;
        }
        return totalPage;
    }

    /**
     * 获取查询起始条目编号
     *
     * @return 查询起始条目编号
     */
    public int getStartIndex() {
        if (getCurrentPage() > 0) {
            if (getPageSize() > 0) {
                startIndex = (getCurrentPage() - 1) * getPageSize();
            } else {
                startIndex = 0;
            }
        } else {
            startIndex = 0;
        }
        return startIndex;
    }

    /**
     * 获取下一页页码
     *
     * @return 下一页页码
     */
    public int getNextPage() {
        if (getCurrentPage() >= getTotalPage()) {
            nextPage = getTotalPage();
        } else {
            if (getTotalPage() == 0) {
                nextPage = 1;
            } else {
                nextPage = getCurrentPage() + 1;
            }

        }
        return nextPage;
    }

    /**
     * 获取上一页页码
     *
     * @return 上一页页码
     */
    public int getPriviousPage() {
        if (getCurrentPage() <= 1) {
            priviousPage = 1;
        } else if (getCurrentPage() > getTotalPage()) {
            priviousPage = getTotalPage() - 1;
        } else {
            priviousPage = getCurrentPage() - 1;
        }
        return priviousPage;
    }

}
