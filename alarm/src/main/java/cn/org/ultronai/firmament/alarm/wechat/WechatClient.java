package cn.org.ultronai.firmament.alarm.wechat;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import cn.hutool.http.Method;
import cn.org.ultronai.firmament.alarm.wechat.model.WechatMessageReq;
import cn.org.ultronai.firmament.alarm.wechat.model.WechatMessageResp;
import cn.org.ultronai.firmament.commonapi.http.Client;
import cn.org.ultronai.firmament.commonapi.http.HttpClientImpl;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/06 15:04
 */
public class WechatClient {
    private static final Client HTTP_CLIENT = HttpClientImpl.getInstance();

    public static WechatMessageResp send(String alertAddress, WechatMessageReq wechatMessageReq) {
        try {
            Client.RpcRequest rpcRequest = new Client.RpcRequest(alertAddress, wechatMessageReq, new HashMap<>(), Method.POST, 5, TimeUnit.SECONDS, 0);
            return HTTP_CLIENT.call(rpcRequest, WechatMessageResp.class);
        } catch (Exception e) {
            return new WechatMessageResp(-1, e.getMessage());
        }
    }
}
