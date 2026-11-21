package week_02;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class CipherUtil {

    private static final SecureRandom RANDOM = new SecureRandom();

    private CipherUtil() {
    }

    public static byte[] utf8(String s) {
        return s.getBytes(StandardCharsets.UTF_8);
    }

    public static String text(byte[] b) {
        return new String(b, StandardCharsets.UTF_8);
    }

    public static String b64(byte[] b) {
        return Base64.getEncoder().encodeToString(b);
    }

    public static byte[] fromB64(String s) {
        try {
            return Base64.getDecoder().decode(s.trim());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Dữ liệu không phải Base64 hợp lệ.");
        }
    }

    public static byte[] randomBytes(int n) {
        byte[] b = new byte[n];
        RANDOM.nextBytes(b);
        return b;
    }

    public static String hex(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(String.format("%02X", b & 0xFF));
        }
        return sb.toString();
    }

    public static byte[] fromHex(String hex) {
        String s = hex.trim();
        if (s.length() % 2 != 0 || !s.matches("[0-9A-Fa-f]*")) {
            throw new IllegalArgumentException("Chuỗi HEX không hợp lệ (chỉ gồm 0-9, A-F và có số ký tự chẵn).");
        }
        byte[] out = new byte[s.length() / 2];
        for (int i = 0; i < out.length; i++) {
            out[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
        }
        return out;
    }

    public static void requireKey(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Hãy nhập Key.");
        }
    }

    public static byte[] concat(byte[] a, byte[] b) {
        byte[] out = new byte[a.length + b.length];
        System.arraycopy(a, 0, out, 0, a.length);
        System.arraycopy(b, 0, out, a.length, b.length);
        return out;
    }

    public static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bits) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bits);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (Exception ex) {
            throw new IllegalStateException("Không tạo được khóa PBKDF2", ex);
        }
    }
}