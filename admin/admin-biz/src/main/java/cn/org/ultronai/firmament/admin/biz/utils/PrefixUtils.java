package cn.org.ultronai.firmament.admin.biz.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.hutool.core.util.IdUtil;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/06 17:15
 */
public class PrefixUtils {
    private static final String ACCESS_TOKEN_PREFIX    = "AT@";
    private static final String MODEL_ID_PREFIX        = "MODEL@";
    private static final String ALERT_PREFIX           = "ALERT@";
    private static final String REAL_TIME_MODEL_PREFIX = "RTM@";
    private static final String ORDER_PREFIX           = "OD";
    private static final String PROMPT_PREFIX          = "PROMPT@";

    /**
     * 获取无状态访问token
     *
     * @return 无状态访问token
     */
    public static String genAccessToken(String memberId) {
        return ACCESS_TOKEN_PREFIX + IdUtil.fastSimpleUUID().toUpperCase() + "@" + memberId;
    }

    /**
     * 获取无状态访问token
     *
     * @return 无状态访问token
     */
    public static String genModelId(String memberId) {
        return MODEL_ID_PREFIX + IdUtil.fastSimpleUUID().toUpperCase() + "@" + memberId;
    }

    public static String genAlertId(String memberId) {
        return ALERT_PREFIX + IdUtil.fastSimpleUUID().toUpperCase() + "@" + memberId;
    }

    public static String genRealTimeModelId(String memberId) {
        return REAL_TIME_MODEL_PREFIX + IdUtil.fastSimpleUUID().toUpperCase() + "@" + memberId;
    }

    public static String genOrderNo(String memberId) {
        SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
        return ORDER_PREFIX + yyyyMMddHHmmss.format(new Date()) + IdUtil.fastSimpleUUID().toUpperCase();
    }

    public static String genPromptId(String memberId) {
        return PROMPT_PREFIX + IdUtil.fastSimpleUUID().toUpperCase() + "@" + memberId;
    }
}
