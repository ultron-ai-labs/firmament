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
package cn.org.ultronai.firmament.support.indicator.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * OHLCV数据模型类，用于表示金融市场的时间序列数据
 * 包含开盘价、最高价、最低价、收盘价和成交量
 */
@Data
public class OHLCV {

    /**
     * 时间戳，代表数据点的时间
     */
    private Date   timestamp;

    /**
     * 时间戳，代表数据点时间
     */
    private long   openTime;

    /**
     * 开盘价
     */
    private double open;

    /**
     * 最高价
     */
    private double high;

    /**
     * 最低价
     */
    private double low;

    /**
     * 收盘价
     */
    private double close;

    /**
     * 成交量
     */
    private double volume;

    public OHLCV(Long openTime, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close, BigDecimal volume) {
        this.timestamp = new Date(openTime);
        this.openTime = openTime;
        this.open = open.doubleValue();
        this.high = high.doubleValue();
        this.low = low.doubleValue();
        this.close = close.doubleValue();
        this.volume = volume.doubleValue();
    }
}