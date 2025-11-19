package cn.org.ultronai.firmament.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 报警
 *
 * @author icanci
 * @since 1.0 Created in 2025/11/05 22:07
 */
@Getter
@AllArgsConstructor
public enum AlertTypeEnum {
                           /**
                            * 企业微信
                            * wiki: https://developer.work.weixin.qq.com/document/path/91770
                            */
                           WECHAT("wechat"),
                           /**
                            * 钉钉群
                            * wiki: https://open.dingtalk.com/document/dingstart/custom-bot-creation-and-installation
                            */
                           DINGDING("dingding"),
                           /**
                           * 飞书
                            * wiki: https://open.feishu.cn/document/client-docs/bot-v3/add-custom-bot
                           */
                           FEISHU("feishu");

    //
    ;

    private final String type;

    public static AlertTypeEnum getByType(String type) {
        for (AlertTypeEnum value : values()) {
            if (value.type.equals(type)) {
                return value;
            }
        }
        return null;
    }
}
