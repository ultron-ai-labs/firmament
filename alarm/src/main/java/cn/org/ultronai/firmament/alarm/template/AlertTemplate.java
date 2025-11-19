package cn.org.ultronai.firmament.alarm.template;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/06 15:48
 */
public class AlertTemplate {
    /**
     * 回测结果报警模板
     *
     * @param alertContent 报警内容
     * @return 报警内容
     */
    public static String backtestAlertTemplateForStart(String alertContent) {
        // 回测计划「%s」已经开始
        // 币种：
        // 周期：
        // 级别：
        // 预计完成时间：
        // 预计消耗Token：
        return "【回测结果】" + alertContent;
    }

    /**
    * 回测结果报警模板
    *
    * @param alertContent 报警内容
    * @return 报警内容
    */
    public static String backtestAlertTemplateForEnd(String alertContent) {
        // 回测计划「%s」已经开始
        // 币种：
        // 周期：
        // 级别：
        // 预计完成时间：
        // 预计消耗Token：
        return "【回测结果】" + alertContent;
    }
}
