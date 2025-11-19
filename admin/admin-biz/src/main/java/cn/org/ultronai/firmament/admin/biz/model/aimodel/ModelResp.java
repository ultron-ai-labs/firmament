package cn.org.ultronai.firmament.admin.biz.model.aimodel;

import lombok.Data;

import java.util.Date;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 09:27
 */
@Data
public class ModelResp {
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
     * 模型关联ID
     */
    private String modelId;
    /**
     * 测试的模型分类：对话模型
     */
    private String testCategory;;
    /**
     * 创建时间
     */
    private Date   createTime;
}
