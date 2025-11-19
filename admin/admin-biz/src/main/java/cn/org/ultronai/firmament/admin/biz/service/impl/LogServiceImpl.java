package cn.org.ultronai.firmament.admin.biz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.hutool.json.JSONUtil;
import cn.org.ultronai.firmament.admin.biz.model.log.LogReq;
import cn.org.ultronai.firmament.admin.biz.model.log.LogReqResp;
import cn.org.ultronai.firmament.admin.biz.service.LogService;
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.LogDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.LogDO;
import cn.org.ultronai.firmament.aichat.common.ChatChoice;
import cn.org.ultronai.firmament.aichat.common.ChatMessage;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 20:51
 */
@Service("logService")
public class LogServiceImpl implements LogService {
    private static final String MODULE_AI_CHAT      = "ai_chat";
    private static final String MODULE_AI_CHAT_REQ  = "ai_chat_req";
    private static final String MODULE_AI_CHAT_RESP = "ai_chat_resp";
    private static final String MODULE_ORDER        = "ai_order";
    private static final String MODULE_ALERT        = "ai_alert";
    @Resource
    private LogDAO              logDAO;

    @Override
    public void logChatReq(String memberId, String strategyId, String model, List<ChatMessage> chatMessage) {
        Map<String, Object> content = new HashMap<>();
        content.put("model", model);
        content.put("messages", chatMessage);
        LogDO logDO = new LogDO();
        logDO.setModule(MODULE_AI_CHAT);
        logDO.setTargetId(strategyId);
        logDO.setOperatorType(MODULE_AI_CHAT_REQ);
        logDO.setContent(JSONUtil.toJsonStr(content));
        logDO.setMemberId(memberId);
        logDAO.insert(logDO);
    }

    @Override
    public void logChatResp(String memberId, String strategyId, String model, List<ChatChoice> chatChoices) {
        Map<String, Object> content = new HashMap<>();
        content.put("model", model);
        content.put("choices", chatChoices);
        LogDO logDO = new LogDO();
        logDO.setModule(MODULE_AI_CHAT);
        logDO.setTargetId(strategyId);
        logDO.setOperatorType(MODULE_AI_CHAT_RESP);
        logDO.setContent(JSONUtil.toJsonStr(content));
        logDO.setMemberId(memberId);
        logDAO.insert(logDO);
    }

    @Override
    public void logOrder(String orderSerialNo, String memberId, String signal) {
        Map<String, Object> content = new HashMap<>();
        content.put("signal", signal);
        LogDO logDO = new LogDO();
        logDO.setModule(MODULE_ORDER);
        logDO.setTargetId(orderSerialNo);
        logDO.setOperatorType("signal");
        logDO.setContent(JSONUtil.toJsonStr(content));
        logDO.setMemberId(memberId);
        logDAO.insert(logDO);
    }

    @Override
    public void logAlert(String alertId, String memberId, String message) {
        Map<String, Object> content = new HashMap<>();
        content.put("message", message);
        LogDO logDO = new LogDO();
        logDO.setModule(MODULE_ALERT);
        logDO.setTargetId(alertId);
        logDO.setOperatorType("alert");
        logDO.setContent(JSONUtil.toJsonStr(content));
        logDO.setMemberId(memberId);
        logDAO.insert(logDO);
    }

    @Override
    public LogReqResp getLog(String accessToken, LogReq logReq) {
        // TODO 返回list或者分页数据
        return new LogReqResp();
    }
}
