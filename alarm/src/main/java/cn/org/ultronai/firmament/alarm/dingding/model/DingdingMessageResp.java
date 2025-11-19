package cn.org.ultronai.firmament.alarm.dingding.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {
 * 	"errcode":"0",
 * 	"errmsg":"ok"
 * }
 * @author icanci
 * @since 1.0 Created in 2025/11/06 13:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DingdingMessageResp {
    private String errcode;
    private String errmsg;

    public boolean isSuccess() {
        return "0".equals(errcode);
    }
}
