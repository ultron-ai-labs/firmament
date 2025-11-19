package cn.org.ultronai.firmament.aichat;

import cn.org.ultronai.firmament.aichat.alibailian.AliBailianChatServiceImpl;
import cn.org.ultronai.firmament.aichat.deepseek.DeepSeekChatServiceImpl;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 10:30
 */
public class AiChatServiceProxy {
    public static AiChatService getAiChat(AIModelTypeEnum modelType) {
        switch (modelType) {
            case DEEP_SEEK:
                return new DeepSeekChatServiceImpl();
            case ALI_BAILIAN:
                return new AliBailianChatServiceImpl();
            default:
                throw new RuntimeException("不支持的模型类型");
        }
    }
}
