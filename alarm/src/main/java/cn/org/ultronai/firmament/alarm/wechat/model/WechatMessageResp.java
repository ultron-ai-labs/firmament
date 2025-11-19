package cn.org.ultronai.firmament.alarm.wechat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/06 14:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WechatMessageResp {
    private int    errcode;
    private String errmsg;

    public boolean isSuccess() {
        return errcode == 0;
    }
}
