package cn.org.ultronai.firmament.admin.biz.service;

import java.util.List;

import cn.org.ultronai.firmament.admin.biz.model.log.LogReq;
import cn.org.ultronai.firmament.admin.biz.model.log.LogReqResp;
import cn.org.ultronai.firmament.aichat.common.ChatChoice;
import cn.org.ultronai.firmament.aichat.common.ChatMessage;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 20:50
 */
public interface LogService {
    void logChatReq(String memberId, String strategyId, String model, List<ChatMessage> messages);

    void logChatResp(String memberId, String strategyId, String model, List<ChatChoice> chatChoices);

    void logOrder(String orderSerialNo, String memberId, String signal);

    void logAlert(String alertId, String memberId, String message);

    LogReqResp getLog(String accessToken, LogReq logReq);
}
