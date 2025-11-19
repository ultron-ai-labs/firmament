package cn.org.ultronai.firmament.admin.biz.model.alert;

import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 15:50
 */
@Data
public class AlertWebReq {
    /**
     * 告警名称
     */
    private String alertName;
    /**
     * 告警类型
     */
    private String alertType;
    /**
     * 告警地址
     */
    private String alertAddress;
    /**
     * 告警sign
     */
    private String alertSign;
    /**
     * 告警ID
     */
    private String alertId;
}
