package cn.org.ultronai.firmament.admin.dal.mongo.dateobject;

import lombok.Data;

/**
 * 告警数据对象
 *
 * @author icanci
 * @since 1.0 Created in 2025/11/06 08:01
 */
@Data
public class AlertDO extends BaseDO {
    /**
     * 告警ID
     */
    private String alertId;
    /**
     * 告警类型 枚举：企业微信、钉钉、飞书、邮件
     * see {@link cn.org.ultronai.firmament.alarm.AlertTypeEnum}
     */
    private String alertType;
    /**
     * 告警地址
     */
    private String alertAddress;
    /**
     * 告警签名
     */
    private String alertSign;
    /**
     * 告警群名称
     */
    private String alertName;
}
