package cn.org.ultronai.firmament.exchange.okx;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author icanci
 * @since 1.0 Created in 2025/10/30 08:13
 */
public class SignatureUtil {

    /**
     * 生成OKX API签名
     */
    public static String generateSignature(String secretKey, String timestamp, String method, String requestPath, String body) {
        try {
            String prehash = timestamp + method.toUpperCase() + requestPath + body;
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hash = mac.doFinal(prehash.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("生成签名失败", e);
        }
    }

    /**
     * 生成时间戳 (ISO 8601格式)
     */
    public static String getTimestamp() {
        return Instant.now().toString().substring(0, 23) + "Z";
    }

    /**
     * Base64编码passphrase
     */
    public static String encodePassphrase(String passphrase) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(passphrase.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hash = mac.doFinal(passphrase.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("编码passphrase失败", e);
        }
    }
}