package cn.org.ultronai.firmament.admin.biz.repository;

import java.util.StringJoiner;

import cn.org.ultronai.firmament.commonapi.model.TimeUnitEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/13 15:52
 */
@Data
@EqualsAndHashCode
class KlineManagerKey {
    /** 币种 */
    private final String       symbol;
    /** 时间间隔 */
    private final TimeUnitEnum timeUnit;
    /** 交易所 */
    private final String       exchange;

    public KlineManagerKey(String symbol, TimeUnitEnum timeUnit, String exchange) {
        this.symbol = symbol;
        this.timeUnit = timeUnit;
        this.exchange = exchange;
    }

    @Override
    public String toString() {
        return new StringJoiner(",").add("symbol=" + symbol).add("timeUnit=" + timeUnit).add("exchange=" + exchange).toString();
    }
}
