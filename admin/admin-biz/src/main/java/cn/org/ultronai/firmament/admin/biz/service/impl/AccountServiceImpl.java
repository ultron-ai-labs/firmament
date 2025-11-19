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
package cn.org.ultronai.firmament.admin.biz.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.AlternativeJdkIdGenerator;

import com.google.common.collect.Sets;

import cn.org.ultronai.firmament.admin.biz.enums.PermissionTypeEnum;
import cn.org.ultronai.firmament.admin.biz.model.account.*;
import cn.org.ultronai.firmament.admin.biz.service.AccountService;
import cn.org.ultronai.firmament.admin.biz.utils.CryptoUtils;
import cn.org.ultronai.firmament.admin.biz.utils.PrefixUtils;
import cn.org.ultronai.firmament.admin.dal.common.PageList;
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.UserDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.UserDO;

/**
 * 账号服务实现类
 * 
 * @author kai
 * @since 1.0 Created in 2025/11/05 15:00
 */
@Service
public class AccountServiceImpl implements AccountService {
    private static final AlternativeJdkIdGenerator ID_GENERATOR       = new AlternativeJdkIdGenerator();
    private static final String                    ACCOUNT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDBT2vr+dhZElF73FJ6xiP181txKWUSNLPQQlid6DUJhGAOZblluafIdLmnUyKE8mMHhT3R+Ib3ssZcJku6Hn72yHYj/qPkCGFv0eFo7G+GJfDIUeDyalBN0QsuiE/XzPHJBuJDfRArOiWvH0BXOv5kpeXSXM8yTt5Na1jAYSiQ/wIDAQAB";
    @Resource
    private UserDAO                                userDAO;

    @Value("${admin}")
    private String                                 adminUsername;

    @Override
    public String publicKey() {
        // TODO: 实现获取公钥的逻辑
        return ACCOUNT_PUBLIC_KEY;
    }

    /**
     * 返回登录数据
     *
     * @param accountLogin accountLogin
     * @return 登录数据
     */
    @Override
    public String login(AccountLoginReq accountLogin) {
        // 查询账户是否存在
        UserDO userDO = userDAO.queryByUserName(accountLogin.getUsername());
        if (userDO == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!userDO.getPassword().equals(CryptoUtils.md5(accountLogin.getPassword()))) {
            throw new RuntimeException("密码错误");
        }
        // 返回用户的 accessToken
        return userDO.getAccessToken();
    }

    /**
     * 返回用户基本数据
     *
     * @param accessToken accessToken
     * @return 登录数据
     */
    @Override
    public AccountLoginResp userInfo(String accessToken) {
        UserDO userDO = userDAO.queryByAccessToken(accessToken);
        if (userDO == null) {
            throw new RuntimeException("用户不存在");
        }
        boolean isAdmin = Arrays.stream(adminUsername.split(",")).anyMatch(username -> username.equals(userDO.getUserName()));
        Set<String> permissions = userDO.getPermissions();
        if (isAdmin) {
            permissions.add(PermissionTypeEnum.ADMIN.getPermission());
            permissions.add(PermissionTypeEnum.PREVIEW.getPermission());
        }
        AccountLoginResp accountLoginResp = new AccountLoginResp();
        accountLoginResp.setMemberId(userDO.getMemberId());
        accountLoginResp.setUsername(userDO.getUserName());
        accountLoginResp.setPermissions(permissions);
        accountLoginResp.setEmail(userDO.getEmail());
        accountLoginResp.setPhone(userDO.getPhone());
        return accountLoginResp;
    }

    /**
     * 注册用户
     *
     * @param req 注册信息
     * @return 注册结果
     */
    @Override
    public String register(AccountRegisterReq req) {
        // 先查询username是否存在
        UserDO userDO = userDAO.queryByUserName(req.getUsername());
        if (userDO != null) {
            throw new RuntimeException("用户已存在");
        }
        userDO = new UserDO();
        userDO.setMemberId(ID_GENERATOR.generateId().toString());
        userDO.setUserName(req.getUsername());
        userDO.setPassword(CryptoUtils.md5(req.getPassword()));
        userDO.setAccessToken(PrefixUtils.genAccessToken(userDO.getMemberId()));
        userDO.setEmail(req.getEmail());
        userDO.setAreaCode(req.getAreaCode());
        userDO.setPhone(req.getPhone());
        userDO.setPermissions(Sets.newHashSet(PermissionTypeEnum.EDITOR.getPermission()));
        userDAO.insert(userDO);
        return userDO.getAccessToken();
    }

    /**
     * 分页查询用户
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 用户列表
     */
    @Override
    public PageList<AccountUserInfoResp> listUsers(int pageNum, int pageSize) {
        PageList<UserDO> userDOPageList = userDAO.pageQuery(new UserDO(), pageNum, pageSize);
        PageList<AccountUserInfoResp> userPageList = new PageList<>();
        userPageList.setPaginator(userDOPageList.getPaginator());
        userPageList.setData(mapperData(userDOPageList.getData()));
        return userPageList;
    }

    /**
     * 修改密码
     *
     * @param accessToken accessToken
     * @param req         修改密码信息
     */
    @Override
    public void changePassword(String accessToken, AccountChangePasswordReq req) {
        UserDO userDO = userDAO.queryByAccessToken(accessToken);
        if (userDO == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!userDO.getPassword().equals(CryptoUtils.md5(req.getOldPassword()))) {
            throw new RuntimeException("密码错误");
        }
        userDO.setPassword(CryptoUtils.md5(req.getNewPassword()));
        userDAO.update(userDO);
        // 重新设置 accessToken 需要用户重新登录
        userDO.setAccessToken(PrefixUtils.genAccessToken(userDO.getMemberId()));
    }

    /**
     * 修改用户信息
     *
     * @param accessToken accessToken
     * @param req         修改用户信息
     */
    @Override
    public void changeUserInfo(String accessToken, AccountChangeUserInfoReq req) {
        UserDO userDO = userDAO.queryByAccessToken(accessToken);
        if (userDO == null) {
            throw new RuntimeException("用户不存在");
        }
        userDO.setEmail(req.getEmail());
        userDO.setPhone(req.getPhone());
        userDAO.update(userDO);
    }

    /**
     * 数据映射
     *
     * @param data 数据
     * @return 映射后的数据
     */
    private List<AccountUserInfoResp> mapperData(Collection<UserDO> data) {
        return data.stream().map(userDO -> {
            AccountUserInfoResp accountUserInfoResp = new AccountUserInfoResp();
            accountUserInfoResp.setMemberId(userDO.getMemberId());
            accountUserInfoResp.setUsername(userDO.getUserName());
            accountUserInfoResp.setEmail(userDO.getEmail());
            accountUserInfoResp.setPhone(userDO.getPhone());
            accountUserInfoResp.setAreaCode(userDO.getAreaCode());
            accountUserInfoResp.setPermissions(userDO.getPermissions());
            return accountUserInfoResp;
        }).collect(Collectors.toList());
    }
}