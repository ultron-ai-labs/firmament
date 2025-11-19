package cn.org.ultronai.firmament.exchange.okx;

/**
 * okx api key
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/14 15:45
 */
public interface OkxApiKey {
    String BASE_URL = "https://www.okx.com";

    interface Market {

        default String candles(String instId, String bar, int limit) {
            return BASE_URL + "/api/v5/market/candles?instId=" + instId + "&bar=" + bar + "&limit=" + limit;
        }
    }

    interface Account {

    }
}
