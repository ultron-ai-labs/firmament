package cn.org.ultronai.firmament.aichat.deepseek;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.map.MapUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONUtil;
import cn.org.ultronai.firmament.aichat.deepseek.model.DeepSeekChatRequest;
import cn.org.ultronai.firmament.aichat.deepseek.model.DeepSeekChatResponse;
import cn.org.ultronai.firmament.aichat.trade.ChatWrapper;
import cn.org.ultronai.firmament.commonapi.http.Client;
import lombok.extern.slf4j.Slf4j;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 10:20
 */
@Slf4j
public class DeepSeekChatServiceImpl extends DeepSeekChatService {
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
        DeepSeekChatRequest deepSeekChatRequest = new DeepSeekChatRequest();
        deepSeekChatRequest.setMessages(buildTestMessage());
        deepSeekChatRequest.setModel(category);
        Client.RpcRequest rpcRequest = new Client.RpcRequest(modelAddress, deepSeekChatRequest, newHeader.map(), Method.POST, 1, TimeUnit.MINUTES, 0);
        DeepSeekChatResponse call = CLIENT.call(rpcRequest, DeepSeekChatResponse.class);
        stopWatch.stop();
        log.info("resp:" + JSONUtil.toJsonStr(call));
        log.info("total:" + stopWatch.getTotalTimeSeconds());
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
        DeepSeekChatRequest deepSeekChatRequest = new DeepSeekChatRequest();
        deepSeekChatRequest.setMessages(buildChatMessage(systemPrompt, userPrompt));
        deepSeekChatRequest.setModel(category);
        Client.RpcRequest rpcRequest = new Client.RpcRequest(modelAddress, deepSeekChatRequest, newHeader.map(), Method.POST, 5, TimeUnit.MINUTES, 0);
        DeepSeekChatResponse call = CLIENT.call(rpcRequest, DeepSeekChatResponse.class);
        return new ChatWrapper(deepSeekChatRequest, call);
    }

}
