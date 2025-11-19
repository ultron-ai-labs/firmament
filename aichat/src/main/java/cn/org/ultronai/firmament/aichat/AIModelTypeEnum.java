package cn.org.ultronai.firmament.aichat;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 10:03
 */
@Getter
@AllArgsConstructor
public enum AIModelTypeEnum {
                             // 
                             DEEP_SEEK("DeepSeek"),
                             //
                             ALI_BAILIAN("AliBailian");

    private final String modelType;

    public static AIModelTypeEnum getByModelType(String modelType) {
        for (AIModelTypeEnum value : values()) {
            if (value.modelType.equals(modelType)) {
                return value;
            }
        }
        return null;
    }
}
