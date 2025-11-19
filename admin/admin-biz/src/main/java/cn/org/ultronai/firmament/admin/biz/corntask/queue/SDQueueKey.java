package cn.org.ultronai.firmament.admin.biz.corntask.queue;

import cn.org.ultronai.firmament.commonapi.model.TimeUnitEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 异同队列模型
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/13 17:42
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
public class SDQueueKey {
    /**
     * 交易所
     */
    private final String       exchange;
    /**
     * 币种
     */
    private final TimeUnitEnum timeUnit;
}
