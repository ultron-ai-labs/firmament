package cn.org.ultronai.firmament.alarm;

import cn.org.ultronai.firmament.alarm.model.AlertReq;
import cn.org.ultronai.firmament.alarm.model.AlertResp;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/06 11:22
 */
public interface AlertHandler {

    /**
     * 处理告警
     *
     * @param req 告警请求
     * @return 告警结果
     */
    AlertResp handle(AlertReq req);
}
