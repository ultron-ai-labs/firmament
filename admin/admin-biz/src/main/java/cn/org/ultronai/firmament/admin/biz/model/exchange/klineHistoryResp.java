package cn.org.ultronai.firmament.admin.biz.model.exchange;

import java.io.Serializable;

import lombok.Data;

/**
 * k线历史数据返回参数
 *
 * @author icanci
 * @since 1.0 Created in 2025/11/07 17:02
 */
@Data
public class klineHistoryResp implements Serializable {
    private String cryptoCode;
    private String exchange;
    private String timeUnit;
    private long openTime;
    private double open;
    private double high;
    private double low;
    private double close;
    private double volume;
}
