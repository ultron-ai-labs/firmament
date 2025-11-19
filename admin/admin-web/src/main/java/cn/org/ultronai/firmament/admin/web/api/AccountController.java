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
package cn.org.ultronai.firmament.admin.web.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;

import cn.org.ultronai.firmament.admin.biz.model.account.*;
import cn.org.ultronai.firmament.admin.biz.service.AccountService;
import cn.org.ultronai.firmament.admin.dal.common.R;
import lombok.extern.slf4j.Slf4j;

/**
 * 账号管理
 * 
 * 默认头像：https://gcore.jsdelivr.net/gh/zxwk1998/image/avatar/avatar_1.png
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/04 14:28
 */
@Slf4j
@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Resource
    private AccountService accountService;

    @PostMapping("/publicKey")
    public R publicKey() {
        // 返回公钥数据
        return R.builderOk().data("publicKey", accountService.publicKey()).build();
    }

    @PostMapping("/login")
    public R login(@RequestBody AccountLoginReq accountLogin) {
        try {
            return R.builderOk().data("accessToken", accountService.login(accountLogin)).build();
        } catch (Exception e) {
            log.error("登录失败", e);
            return R.builderFail().message(e.getMessage()).build();
        }
    }

    @PostMapping("/register")
    public R register(@RequestBody AccountRegisterReq req) {
        try {
            return R.builderOk().message("注册成功").data("accessToken", accountService.register(req)).build();
        } catch (Exception e) {
            log.error("注册用户信息失败", e);
            return R.builderFail().message("注册用户信息失败").build();
        }
    }

    @PostMapping("/userInfo")
    public R userInfo(@RequestBody AccountUserInfoReq req) {
        try {
            return R.builderOk().message("查询成功").data("data", accountService.userInfo(req.getAccessToken())).build();
        } catch (Exception e) {
            log.error("查询用户信息失败", e);
            return R.builderFail().message("查询用户信息失败").build();
        }
    }

    @PostMapping("/logout")
    public R logout(@RequestHeader String accessToken) {
        try {
            // TODO 记录登出日志
            AccountLoginResp accountLoginResp = accountService.userInfo(accessToken);
            return R.builderOk().message("退出成功").build();
        } catch (Exception e) {
            log.error("退出失败", e);
            return R.builderFail().message("退出失败").build();
        }
    }

    /**
     * 修改密码 TODO 前端待接入
     */
    @PostMapping("/changePassword")
    public R changePassword(@RequestHeader String accessToken, @RequestBody AccountChangePasswordReq req) {
        try {
            accountService.changePassword(accessToken, req);
            return R.builderOk().message("修改密码成功").build();
        } catch (Exception e) {
            log.error("修改密码失败", e);
            return R.builderFail().message("修改密码失败").build();
        }
    }

    /**
     * TODO 前端待接入
     */
    @PostMapping("/changeUserInfo")
    public R changeUserInfo(@RequestHeader String accessToken, @RequestBody AccountChangeUserInfoReq req) {
        try {
            accountService.changeUserInfo(accessToken, req);
            return R.builderOk().message("修改资料成功").build();
        } catch (Exception e) {
            log.error("修改资料失败", e);
            return R.builderFail().message("修改资料失败").build();
        }
    }

    // TODO 用户的权限管理和列表页，待处理～
}
