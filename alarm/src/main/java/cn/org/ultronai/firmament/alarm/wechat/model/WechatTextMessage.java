package cn.org.ultronai.firmament.alarm.wechat.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/06 14:59
 */
@Data
@AllArgsConstructor
public class WechatTextMessage {
    // 消息内容
    private String content;
}
