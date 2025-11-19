package cn.org.ultronai.firmament.admin.biz.model.aimodel;

import cn.org.ultronai.firmament.admin.biz.model.BasePageReq;
import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 09:44
 */
@Data
public class ModelReq extends BasePageReq {
    /**
     * 模型名称
     */
    private String name;

}
