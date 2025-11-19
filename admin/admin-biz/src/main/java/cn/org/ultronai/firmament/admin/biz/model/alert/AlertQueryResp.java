package cn.org.ultronai.firmament.admin.biz.model.alert;

import java.util.List;

import cn.org.ultronai.firmament.admin.biz.model.BasePageResp;
import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 15:43
 */
@Data
public class AlertQueryResp extends BasePageResp {
    private List<AlertRecordResp> records;

    public AlertQueryResp(List<AlertRecordResp> records, Integer total, Integer page, Integer pageSize) {
        super(total, page, pageSize);
        this.records = records;
    }
}
