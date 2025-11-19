package cn.org.ultronai.firmament.admin.biz.model.alert;

import cn.org.ultronai.firmament.admin.biz.model.BasePageReq;
import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 15:43
 */
@Data
public class AlertQueryReq extends BasePageReq {
    private String alertName;
    private String alertType;
}
