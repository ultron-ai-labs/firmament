package cn.org.ultronai.firmament.alarm.dingding;

import java.net.URLEncoder;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import cn.org.ultronai.firmament.alarm.AlertHandler;
import cn.org.ultronai.firmament.alarm.dingding.model.DingdingMessageReq;
import cn.org.ultronai.firmament.alarm.dingding.model.DingdingMessageResp;
import cn.org.ultronai.firmament.alarm.dingding.model.DingdingTextMessage;
import cn.org.ultronai.firmament.alarm.model.AlertReq;
import cn.org.ultronai.firmament.alarm.model.AlertResp;

/**
 * 请求地址样例
 * https://oapi.dingtalk.com/robot/send?access_token=XXXXXX&timestamp=XXX&sign=XXX
 * 用户配置的地址应该是：https://oapi.dingtalk.com/robot/send?access_token=XXXXXX&sign=XXX
 * 钉钉机器人的access_token和sign是动态的，需要根据实际环境进行配置
 * 然后timestamp需要实时更新
 *
 * @author icanci
 * @since 1.0 Created in 2025/11/06 11:23
 */
@SuppressWarnings("all")
public class DingdingAlertHandler implements AlertHandler {
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
        long currentTimeMillis = System.currentTimeMillis();
        String realAlertAddress = alertAddress + "&sign=" + genSign(req.getSign(), currentTimeMillis) + "&timestamp=" + currentTimeMillis;
        DingdingMessageReq dingdingMessage = buildReq(alertContent);
        DingdingMessageResp dingdingMessageResp = DingdingClient.send(realAlertAddress, dingdingMessage);
        return new AlertResp(req, dingdingMessageResp);
    }

    private String genSign(String secret, long currentTimeMillis) {
        try {
            String stringToSign = currentTimeMillis + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            return URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("生成签名失败");
        }
    }

    private static DingdingMessageReq buildReq(String alertContent) {
        DingdingMessageReq dingdingMessage = new DingdingMessageReq();
        dingdingMessage.setMsgtype("text");
        dingdingMessage.setText(new DingdingTextMessage(alertContent));
        dingdingMessage.setMsgUuid(UUID.randomUUID().toString());
        return dingdingMessage;
    }
}
