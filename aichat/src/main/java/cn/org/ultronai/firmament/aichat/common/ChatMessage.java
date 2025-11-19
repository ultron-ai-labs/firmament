package cn.org.ultronai.firmament.aichat.common;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/13 10:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage implements Serializable {
    private String role;
    private String content;
}
