package cn.org.ultronai.firmament.admin.biz.model.realtimestrategy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringJoiner;

import cn.org.ultronai.firmament.commonapi.model.TimeUnitEnum;
import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/10/29 17:30
 */
@Data
public class WarningResult implements Serializable {
    private String signal;
    private String profit_target;
    private String stop_loss;
    private String current_price;
    private String justification;
    private String quantity;
    private String confidence;
    private String leverage;

    public BigDecimal thisOrderTotal() {
        return new BigDecimal(quantity);
    }

    public String gSignal() {
        if ("sell_to_enter".equals(signal)) {
            return "做空";
        }
        if ("buy_to_enter".equals(signal)) {
            return "做多";
        }
        if ("close_position".equals(signal)) {
            return "平仓";
        }
        if ("hold".equals(signal)) {
            return "观望";
        }
        // close_short_selling_orders
        if ("close_short_orders".equals(signal)) {
            return "平空";
        }
        // close_long_orders
        if ("close_long_orders".equals(signal)) {
            return "平多";
        }
        return "未知";
    }
}
