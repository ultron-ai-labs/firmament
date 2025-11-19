package cn.org.ultronai.firmament.admin.dal.mongo.dateobject;

import lombok.Data;

/**
 * 告警记录数据对象
 *
 * @author icanci
 * @since 1.0 Created in 2025/11/06 08:02
 */
@Data
public class AlertRecordDO extends BaseDO {
    /**
     * 告警id
     */
    private String alertId;
    /**
     * 告警分类
     */
    private String alertCategory;
    /**
     * 告警内容
     */
    private String alertContent;

}
