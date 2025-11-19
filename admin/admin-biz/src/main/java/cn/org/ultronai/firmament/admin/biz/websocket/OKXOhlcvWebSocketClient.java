package cn.org.ultronai.firmament.admin.biz.websocket;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class OKXOhlcvWebSocketClient extends WebSocketClient {

    public OKXOhlcvWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    // https://www.okx.com/docs-v5/zh/#public-data-websocket-index-tickers-channel
    // https://www.okx.com/docs-v5/zh/#public-data-websocket-index-candlesticks-channel
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("✅ 已连接到 OKX WebSocket 服务器");

        // 构造订阅 K线（OHLCV）的数据
        String subscribeMessage = "{\n" + "    \"id\": \"151111112\",\n" + "    \"op\": \"subscribe\",\n" + "    \"args\": [{\n" + "        \"channel\": \"index-candle30m\",\n"
                                  + "        \"instId\": \"BTC-USD\"\n" + "    }]\n" + "}\n";

        // 发送订阅消息
        this.send(subscribeMessage);
        System.out.println("📡 已发送订阅请求：BTC-USDT 1m K线数据");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("📥 收到消息: " + message);
        // TODO: 解析返回的 K线数据，通常是数组，包含时间、开盘、最高、最低、收盘、成交量
        // 例如：
        // [
        //   [
        //     "1690000000000",  // 时间戳（毫秒）
        //     "27000.1",        // 开盘价
        //     "27010.0",        // 最高价
        //     "26990.0",        // 最低价
        //     "27005.5",        // 收盘价
        //     "12.5"            // 成交量（BTC 数量）
        //   ]
        // ]
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("❌ 连接已关闭: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
        System.out.println("⚠️ WebSocket 出现错误");
    }

    // https://www.okx.com/docs-v5/zh/#overview-websocket-subscribe
    public static void main(String[] args) throws Exception {
        // OKX WebSocket Public 数据地址
        URI uri = new URI("wss://ws.okx.com:8443/ws/v5/public");

        OKXOhlcvWebSocketClient client = new OKXOhlcvWebSocketClient(uri);
        client.connect(); // 开始连接
    }
}