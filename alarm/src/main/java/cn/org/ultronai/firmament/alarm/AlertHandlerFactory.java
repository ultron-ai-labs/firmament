package cn.org.ultronai.firmament.alarm;

import cn.org.ultronai.firmament.alarm.dingding.DingdingAlertHandler;
import cn.org.ultronai.firmament.alarm.feishu.FeishuAlertHandler;
import cn.org.ultronai.firmament.alarm.wechat.WechatAlertHandler;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/06 11:23
 */
public final class AlertHandlerFactory {
    private AlertHandlerFactory() {
    }

    /**
     * 获取报警处理器
     *
     * @param alertType 报警类型
     * @return 报警处理器
     */
    public static AlertHandler getAlertHandler(AlertTypeEnum alertType) {
        switch (alertType) {
            case WECHAT:
                return new WechatAlertHandler();
            case DINGDING:
                return new DingdingAlertHandler();
            case FEISHU:
                return new FeishuAlertHandler();
            default:
                throw new IllegalArgumentException("Invalid alert type: " + alertType);
        }
    }
}
