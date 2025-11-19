package cn.org.ultronai.firmament.aichat.common;

import java.io.Serializable;

import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/13 10:43
 */
@Data
public class ChatUsage implements Serializable {
    private int prompt_tokens;
    private int completion_tokens;
    private int total_tokens;
}
