package cn.org.ultronai.firmament.admin.biz.model.aimodel;

import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 09:27
 */
@Data
public class ModelAddReq {
    /**
     * 模型名称
     */
    private String name;
    /**
     * 模型类型 DeepSeek 、OpenAI
     */
    private String modelType;
    /**
     * 模型API地址
     */
    private String apiUrl;
    /**
     * 模型安全Key
     */
    private String apiKey;
    /**
     * 执行测试的模型分类：对话模型
     */
    private String testCategory;
    /**
     * 模型关联ID
     */
    private String modelId;
}
