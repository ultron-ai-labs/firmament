package cn.org.ultronai.firmament.alarm;

import cn.org.ultronai.firmament.alarm.model.AlertReq;
import cn.org.ultronai.firmament.alarm.model.AlertResp;

/**
 * 报警分发器
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/06 11:21
 */
public class AlarmDispatcher {
    /**
     * 分发报警
     *
     * @param req 报警请求
     * @return 报警结果
     */
    public static AlertResp dispatch(AlertReq req) {
        // 获取报警类型对应的报警处理类
        AlertHandler alertHandler = AlertHandlerFactory.getAlertHandler(req.getAlertType());
        // 处理报警
        return alertHandler.handle(req);
    }
}
