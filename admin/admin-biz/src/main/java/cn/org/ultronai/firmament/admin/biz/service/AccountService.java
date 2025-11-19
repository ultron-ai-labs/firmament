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
package cn.org.ultronai.firmament.admin.biz.service;

import cn.org.ultronai.firmament.admin.biz.model.account.*;
import cn.org.ultronai.firmament.admin.dal.common.PageList;

/**
 * 账号服务
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/05 09:27
 */
public interface AccountService {
    /**
     * 获取公钥
     * 
     * @return 公钥
     */
    String publicKey();

    /**
     * 返回登录数据
     *
     * @param accountLogin accountLogin
     * @return 登录数据
     */
    String login(AccountLoginReq accountLogin);

    /**
     * 返回用户基本数据
     *
     * @param accessToken accessToken
     * @return 登录数据
     */
    AccountLoginResp userInfo(String accessToken);

    /**
     * 注册用户
     *
     * @param req 注册信息
     * @return 注册结果
     */
    String register(AccountRegisterReq req);

    /**
     * 分页查询用户
     * 
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 用户列表
     */
    PageList<AccountUserInfoResp> listUsers(int pageNum, int pageSize);

    /**
     * 修改密码
     *
     * @param accessToken accessToken
     * @param req 修改密码信息
     */
    void changePassword(String accessToken, AccountChangePasswordReq req);

    /**
     * 修改用户信息
     *
     * @param accessToken accessToken
     * @param req 修改用户信息
     */
    void changeUserInfo(String accessToken, AccountChangeUserInfoReq req);
}