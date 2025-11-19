package cn.org.ultronai.firmament.alarm.feishu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/06 15:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeishuMessageResp {
    private int    code;
    private String msg;

    public boolean isSuccess() {
        return code == 0;
    }
}
