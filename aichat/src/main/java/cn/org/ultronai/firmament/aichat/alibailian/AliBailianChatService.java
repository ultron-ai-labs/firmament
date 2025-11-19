package cn.org.ultronai.firmament.aichat.alibailian;

import java.util.ArrayList;
import java.util.List;

import cn.org.ultronai.firmament.aichat.AiChatService;
import cn.org.ultronai.firmament.aichat.common.ChatMessage;
import cn.org.ultronai.firmament.commonapi.http.Client;
import cn.org.ultronai.firmament.commonapi.http.HttpClientImpl;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/13 10:23
 */
public abstract class AliBailianChatService implements AiChatService {
    protected static final Client CLIENT = HttpClientImpl.getInstance();

    protected List<ChatMessage> buildTestMessage() {
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system", "你是一个数学老师！"));
        messages.add(new ChatMessage("user", "请帮我计算1+1等于几？"));
        return messages;
    }

    protected List<ChatMessage> buildChatMessage(String systemPrompt, String userPrompt) {
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system", systemPrompt));
        messages.add(new ChatMessage("user", userPrompt));
        return messages;
    }
}
