package cn.org.ultronai.firmament.alarm.model;

import java.io.Serializable;

import cn.org.ultronai.firmament.alarm.AlertTypeEnum;
import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/06 11:27
 */
@Data
public class AlertReq implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 会员Id
     */
    private String            memberId;
    /**
     * 报警类型
     */
    private AlertTypeEnum     alertType;
    /**
     * 告警id
     */
    private String            alertId;
    /**
     * 告警名称
     */
    private String            alertName;
    /**
     * 告警分类
     */
    private String            alertCategory;
    /**
     * 报警地址
     */
    private String            alertAddress;
    /**
     * 报警内容
     */
    private String            alertContent;
    /**
     * 签名
     */
    private String            sign;
}
