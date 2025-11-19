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
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 通用返回结果
 *
 * @author icanci
 * @since 1.0 Created in 2022/04/04 19:11
 */
@SuppressWarnings("all")
public class R implements Serializable {
    private static final long serialVersionUID = -1343013883236338104L;
    private boolean           ok;
    /** 错误码 */
    private int               code;
    /** 错误信息 */
    private String            msg;
    /** 返回前端数据 */
    private Object            data             = new HashMap<>();

    public R() {
    }

    /**
     * Builder
     * 
     * @return Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * BuilderOK
     *
     * @return Builder
     */
    public static Builder builderOk() {
        return new Builder().BuilderOK();
    }

    /**
     * BuilderFail
     *
     * @return Builder
     */
    public static Builder builderFail() {
        return new Builder().BuilderFail();
    }

    public static class Builder {
        /** 是否成功 */
        private boolean ok;
        /** 错误码 */
        private int     code;
        /** 错误信息 */
        private String  message;
        /** 返回前端数据 */
        private Object  data;

        private Builder() {

        }

        public Builder(boolean ok, int code) {
            this.ok = ok;
            this.code = code;
        }

        private Builder BuilderOK() {
            this.ok = true;
            this.code = ResultCodes.SUCCESS;
            return this;
        }

        private Builder BuilderFail() {
            this.ok = false;
            this.code = ResultCodes.FAIL_SYSTEM;
            return this;
        }

        public Builder message(String val) {
            message = val;
            return this;
        }

        public Builder code(Integer val) {
            code = val;
            return this;
        }

        public Builder data(String key, Object value) {
            HashMap<String, Object> map = new HashMap<>();
            map.put(key, value);
            data = map;
            return this;
        }

        public Builder data(Map<String, Object> map) {
            data = map;
            return this;
        }

        public Builder data(Object object) {
            data = object;
            return this;
        }

        public R build() {
            return new R(this);
        }
    }

    private R(Builder builder) {
        this.ok = builder.ok;
        this.code = builder.code;
        this.msg = builder.message;
        this.data = builder.data;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new StringJoiner(",").add("ok=" + ok).add("code=" + code).add("message=" + msg).add("data=" + data).toString();
    }
}
