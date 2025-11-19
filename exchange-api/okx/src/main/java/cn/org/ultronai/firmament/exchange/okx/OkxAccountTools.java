package cn.org.ultronai.firmament.exchange.okx;

import cn.org.ultronai.firmament.commonapi.http.Client;
import cn.org.ultronai.firmament.commonapi.http.HttpClientImpl;

/**
 * okx账户操作工具类
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/14 15:42
 */
public class OkxAccountTools {
    /** http instance */
    private static final Client HTTP_INSTANCE = HttpClientImpl.getInstance();
    /** 基础URL */
    private static final String BASE_URL      = "https://www.okx.com";

}
