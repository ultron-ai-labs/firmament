package cn.org.ultronai.firmament.alarm.wechat;

import cn.org.ultronai.firmament.alarm.AlertHandler;
import cn.org.ultronai.firmament.alarm.model.AlertReq;
import cn.org.ultronai.firmament.alarm.model.AlertResp;
import cn.org.ultronai.firmament.alarm.wechat.model.WechatMessageReq;
import cn.org.ultronai.firmament.alarm.wechat.model.WechatMessageResp;
import cn.org.ultronai.firmament.alarm.wechat.model.WechatTextMessage;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/06 11:23
 */
public class WechatAlertHandler implements AlertHandler {
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
        WechatMessageReq wechatMessageReq = buildReq(alertContent);
        WechatMessageResp wechatMessageResp = WechatClient.send(alertAddress, wechatMessageReq);
        return new AlertResp(req, wechatMessageResp);
    }

    private WechatMessageReq buildReq(String alertContent) {
        WechatMessageReq wechatMessageReq = new WechatMessageReq();
        wechatMessageReq.setMsgtype("text");
        wechatMessageReq.setText(new WechatTextMessage(alertContent));
        return wechatMessageReq;
    }
}
