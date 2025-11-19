package cn.org.ultronai.firmament.aichat.common;

import java.io.Serializable;

import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/13 10:36
 */
@Data
public class ChatChoice implements Serializable {
    private int         index;
    private ChatMessage message;
    private String      logprobs;
    private String      finish_reason;
}
