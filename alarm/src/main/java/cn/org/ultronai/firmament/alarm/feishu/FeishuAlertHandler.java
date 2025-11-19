package cn.org.ultronai.firmament.alarm.feishu;

import cn.org.ultronai.firmament.alarm.AlertHandler;
import cn.org.ultronai.firmament.alarm.feishu.model.FeishuMessageReq;
import cn.org.ultronai.firmament.alarm.feishu.model.FeishuMessageResp;
import cn.org.ultronai.firmament.alarm.feishu.model.FeishuTextMessage;
import cn.org.ultronai.firmament.alarm.model.AlertReq;
import cn.org.ultronai.firmament.alarm.model.AlertResp;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/06 11:24
 */
public class FeishuAlertHandler implements AlertHandler {
    /**
     * 处理告警
     *
     * @param req 告警请求
     * @return 告警结果
     */
    @Override
    public AlertResp handle(AlertReq req) {
        String alertAddress = req.getAlertAddress();
        String alertContent = req.getAlertContent();
        FeishuMessageReq feishuMessageReq = buildReq(alertContent);
        FeishuMessageResp feishuMessageResp = FeishuClient.send(alertAddress, feishuMessageReq);
        return new AlertResp(req, feishuMessageResp);
    }

    private FeishuMessageReq buildReq(String alertContent) {
        FeishuMessageReq feishuMessageReq = new FeishuMessageReq();
        feishuMessageReq.setMsg_type("text");
        feishuMessageReq.setContent(new FeishuTextMessage(alertContent));
        return feishuMessageReq;
    }
}
