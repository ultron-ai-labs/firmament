package cn.org.ultronai.firmament.aichat.alibailian.model;

import java.io.Serializable;
import java.util.List;

import cn.org.ultronai.firmament.aichat.common.ChatMessage;
import lombok.Data;

/**
 * 阿里百练请求参数
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/13 10:26
 */
@Data
public class AliBailianChatRequest implements Serializable {
    private String            model;
    private List<ChatMessage> messages;
}
