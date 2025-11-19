package cn.org.ultronai.firmament.alarm.feishu;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import cn.hutool.http.Method;
import cn.org.ultronai.firmament.alarm.feishu.model.FeishuMessageReq;
import cn.org.ultronai.firmament.alarm.feishu.model.FeishuMessageResp;
import cn.org.ultronai.firmament.commonapi.http.Client;
import cn.org.ultronai.firmament.commonapi.http.HttpClientImpl;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/06 15:29
 */
public class FeishuClient {
    private static final Client HTTP_CLIENT = HttpClientImpl.getInstance();

    /**
     * 发送飞书消息
     *
     * @param alertAddress  飞书地址
     * @param feishuMessageReq 飞书消息模型
     * @return 飞书消息结果
     */
    public static FeishuMessageResp send(String alertAddress, FeishuMessageReq feishuMessageReq) {
        try {
            Client.RpcRequest rpcRequest = new Client.RpcRequest(alertAddress, feishuMessageReq, new HashMap<>(), Method.POST, 5, TimeUnit.SECONDS, 0);
            return HTTP_CLIENT.call(rpcRequest, FeishuMessageResp.class);
        } catch (Exception e) {
            return new FeishuMessageResp(-1, e.getMessage());
        }
    }
}
