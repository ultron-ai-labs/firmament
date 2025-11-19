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
import java.util.List;

/**
 * @author icanci
 * @since 1.0 Created in 2022/11/12 16:38
 */
public class TextValue<T> implements Serializable {
    private static final long serialVersionUID = -3727976523289870284L;

    private String            label;
    private Object            value;
    private T                 data;
    private List              list;

    public TextValue(String label, Object value) {
        this.label = label;
        this.value = value;
    }

    public TextValue(String label, Object value, T data) {
        this.label = label;
        this.value = value;
        this.data = data;
    }

    public TextValue(String label, Object value, T data, List list) {
        this.label = label;
        this.value = value;
        this.data = data;
        this.list = list;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
