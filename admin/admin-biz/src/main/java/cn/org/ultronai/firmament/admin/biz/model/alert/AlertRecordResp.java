package cn.org.ultronai.firmament.admin.biz.model.alert;

import lombok.Data;

import java.util.Date;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 15:45
 */
@Data
public class AlertRecordResp {
    /**
     * 告警ID
     */
    private String alertId;
    /**
     * 告警名称
     */
    private String alertName;
    /**
     * 告警类型
     */
    private String alertType;
    /**
     * 告警内容
     */
    private String alertAddress;
    /**
     * 告警sign
     */
    private String alertSign;
    /**
     * 创建时间
     */
    private Date   createTime;
}
