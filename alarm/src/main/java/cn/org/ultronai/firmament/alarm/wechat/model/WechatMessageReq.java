package cn.org.ultronai.firmament.alarm.wechat.model;

import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/06 14:58
 */
@Data
public class WechatMessageReq {
    private String            msgtype;
    private WechatTextMessage text;
}
