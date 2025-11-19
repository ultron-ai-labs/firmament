package cn.org.ultronai.firmament.admin.biz.model.order;

import cn.org.ultronai.firmament.admin.biz.model.BasePageResp;
import lombok.Data;

import java.util.List;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/10 14:58
 */
@Data
public class OrderPageResp extends BasePageResp {
    private List<OrderResp> records;
}
