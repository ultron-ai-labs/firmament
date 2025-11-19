package cn.org.ultronai.firmament.aichat.alibailian;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.map.MapUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONUtil;
import cn.org.ultronai.firmament.aichat.alibailian.model.AliBailianChatRequest;
import cn.org.ultronai.firmament.aichat.alibailian.model.AliBailianChatResponse;
import cn.org.ultronai.firmament.aichat.trade.ChatWrapper;
import cn.org.ultronai.firmament.commonapi.http.Client;
import lombok.extern.slf4j.Slf4j;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/13 10:25
 */
@Slf4j
public class AliBailianChatServiceImpl extends AliBailianChatService {
    private static final String              API_KEY_PREFIX = "Bearer ";
    private static final Map<String, String> headers        = new HashMap<>();

    static {
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
    }

    /**
     * 测试模型
     *
     * @param modelAddress modelAddress
     * @param modelKey     modelKey
     * @param category     category
     * @return ture/false
     */
    @Override
    public boolean testModel(String modelAddress, String modelKey, String category) {
        MapBuilder<String, String> newHeader = MapUtil.builder(headers);
        newHeader.put("Authorization", API_KEY_PREFIX + modelKey);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        AliBailianChatRequest aliBailianChatRequest = new AliBailianChatRequest();
        aliBailianChatRequest.setMessages(buildTestMessage());
        aliBailianChatRequest.setModel(category);
        Client.RpcRequest rpcRequest = new Client.RpcRequest(modelAddress, aliBailianChatRequest, newHeader.map(), Method.POST, 1, TimeUnit.MINUTES, 0);
        AliBailianChatResponse call = CLIENT.call(rpcRequest, AliBailianChatResponse.class);
        stopWatch.stop();
        log.info("[AliBailianChatService] resp:" + JSONUtil.toJsonStr(call));
        log.info("[AliBailianChatService] total:" + stopWatch.getTotalTimeSeconds());
        return CollectionUtil.isNotEmpty(call.getChoices());
    }

    /**
     * 聊天
     *
     * @param modelAddress modelAddress
     * @param modelKey     modelKey
     * @param category     category
     * @param systemPrompt systemPrompt
     * @param userPrompt   userPrompt
     * @return DeepSeekChatResponse.DeepSeekChatChoice
     */
    @Override
    public ChatWrapper chat(String modelAddress, String modelKey, String category, String systemPrompt, String userPrompt) {
        MapBuilder<String, String> newHeader = MapUtil.builder(headers);
        newHeader.put("Authorization", API_KEY_PREFIX + modelKey);
        AliBailianChatRequest aliBailianChatRequest = new AliBailianChatRequest();
        aliBailianChatRequest.setMessages(buildChatMessage(systemPrompt, userPrompt));
        aliBailianChatRequest.setModel(category);
        Client.RpcRequest rpcRequest = new Client.RpcRequest(modelAddress, aliBailianChatRequest, newHeader.map(), Method.POST, 5, TimeUnit.MINUTES, 0);
        AliBailianChatResponse response = CLIENT.call(rpcRequest, AliBailianChatResponse.class);
        return new ChatWrapper(aliBailianChatRequest, response);
    }
}
