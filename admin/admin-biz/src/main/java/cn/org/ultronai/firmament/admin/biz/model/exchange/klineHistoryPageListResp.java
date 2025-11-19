package cn.org.ultronai.firmament.admin.biz.model.exchange;

import java.util.List;

import cn.org.ultronai.firmament.admin.biz.model.BasePageResp;
import lombok.Data;

/**
 * k线历史数据返回参数
 *         
 * @author icanci
 * @since 1.0 Created in 2025/11/07 17:02
 */
@Data
public class klineHistoryPageListResp extends BasePageResp {
    private static final long      serialVersionUID = 1L;
    /** 数据集 */
    private List<klineHistoryResp> records;

    public klineHistoryPageListResp() {
        super();
    }

    public klineHistoryPageListResp(Integer total, Integer page, Integer pageSize, List<klineHistoryResp> records) {
        super(total, page, pageSize);
        this.records = records;
    }
}
