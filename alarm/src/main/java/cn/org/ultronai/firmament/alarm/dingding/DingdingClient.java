package cn.org.ultronai.firmament.alarm.dingding;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import cn.hutool.http.Method;
import cn.org.ultronai.firmament.alarm.dingding.model.DingdingMessageReq;
import cn.org.ultronai.firmament.alarm.dingding.model.DingdingMessageResp;
import cn.org.ultronai.firmament.commonapi.http.Client;
import cn.org.ultronai.firmament.commonapi.http.HttpClientImpl;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/06 13:38
 */
public class DingdingClient {
    private static final Client HTTP_CLIENT = HttpClientImpl.getInstance();

    public static DingdingMessageResp send(String realAlertAddress, DingdingMessageReq dingdingMessage) {
        try {
            Client.RpcRequest rpcRequest = new Client.RpcRequest(realAlertAddress, dingdingMessage, new HashMap<>(), Method.POST, 5, TimeUnit.SECONDS, 0);
            return HTTP_CLIENT.call(rpcRequest, DingdingMessageResp.class);
        } catch (Exception e) {
            return new DingdingMessageResp("-1", e.getMessage());
        }
    }
}
