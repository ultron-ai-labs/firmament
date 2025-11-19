package cn.org.ultronai.firmament.aichat.trade;

import java.util.List;

import cn.hutool.core.collection.CollectionUtil;
import cn.org.ultronai.firmament.aichat.alibailian.model.AliBailianChatRequest;
import cn.org.ultronai.firmament.aichat.alibailian.model.AliBailianChatResponse;
import cn.org.ultronai.firmament.aichat.common.ChatChoice;
import cn.org.ultronai.firmament.aichat.common.ChatMessage;
import cn.org.ultronai.firmament.aichat.deepseek.model.DeepSeekChatRequest;
import cn.org.ultronai.firmament.aichat.deepseek.model.DeepSeekChatResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 19:39
 */
@Getter
@AllArgsConstructor
public class ChatWrapper {
    // 交易所需要的请求和响应
    private String            reqModel;
    private List<ChatMessage> reqChatMessages;
    // 交易所需要的请求和相应
    private String            respContent;
    private String            respModel;
    private List<ChatChoice>  respChatChoices;

    // 原始请求和响应
    private Object            originalChatRequest;
    private Object            originalChatResponse;

    public ChatWrapper(DeepSeekChatRequest request, DeepSeekChatResponse response) {
        this.originalChatRequest = request;
        this.originalChatResponse = response;
        // 通用模型转换
        fillReqCommon(request.getModel(), request.getMessages());
        fillRespCommon(response.getChoices(), response.getModel());
    }

    public ChatWrapper(AliBailianChatRequest request, AliBailianChatResponse response) {
        this.originalChatRequest = request;
        this.originalChatResponse = response;
        // 通用模型转换
        fillReqCommon(request.getModel(), request.getMessages());
        fillRespCommon(response.getChoices(), response.getModel());
    }

    private void fillReqCommon(String model, List<ChatMessage> messages) {
        this.reqModel = model;
        this.reqChatMessages = messages;
    }

    private void fillRespCommon(List<ChatChoice> chatChoices, String model) {
        if (CollectionUtil.isNotEmpty(chatChoices)) {
            this.respModel = model;
            this.respChatChoices = chatChoices;
            String content = chatChoices.iterator().next().getMessage().getContent();
            this.respContent = content.replace("```json", "").replace("```", "");
        }
    }
}
