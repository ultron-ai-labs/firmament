package cn.org.ultronai.firmament.alarm.feishu.model;

import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/06 15:26
 */
@Data
public class FeishuMessageReq {
    private String            msg_type;
    private FeishuTextMessage content;
}
