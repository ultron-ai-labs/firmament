package cn.org.ultronai.firmament.admin.biz.model.aimodel;

import java.util.List;

import cn.org.ultronai.firmament.admin.biz.model.BasePageResp;
import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 09:27
 */
@Data
public class ModelPageListResp extends BasePageResp {
    private List<ModelResp> records;

    public ModelPageListResp(Integer total, Integer page, Integer pageSize, List<ModelResp> records) {
        super(total, page, pageSize);
        this.records = records;
    }
}
