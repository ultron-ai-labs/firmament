package cn.org.ultronai.firmament.alarm.utils;

import cn.hutool.core.util.IdUtil;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/06 15:06
 */
public class RecordIdGenUtils {
    private static final String PREFIX = "ALERT@";

    public static String genRecordId(String memberId) {
        return PREFIX + IdUtil.fastSimpleUUID().toUpperCase() + "@" + memberId;
    }
}
