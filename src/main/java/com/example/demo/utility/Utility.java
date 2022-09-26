package com.example.demo.utility;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Random;

public class Utility {

    public Utility() {}

    /**
     * 随机数生成器
     * 生成 n 个 [0-9] 之间的随机整数
     * */
    public static String RamNum(int n) {
        StringBuilder sr = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            int num = r.nextInt(9); // 生成[0,9]区间的随机整数
            sr.append(num);
        }
        return sr.toString();
    }


    /**
     * BASE64 编码
     * */
    public static String Encode_BASE64(byte[] data) throws Exception {
        return Base64.getMimeEncoder().encodeToString(data);
    }

    /**
     * BASE64 解码
     * */
    public static byte[] Decode_BASE64(String data) throws Exception {
        return Base64.getMimeDecoder().decode(data);
    }


    /**
     * MD5/SHA-1/SHA-256 编码
     * @param mode "MD5" / "SHA-1" / "SHA-256"
     * */
    public static String Encode_MD5_SHA(String data, String mode) throws Exception {
        MessageDigest md = MessageDigest.getInstance(mode);
        md.update(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : md.digest()) {
            if ((b & 0xff) < 0x10) {
                // 小于0x10的在前方补 "0"
                sb.append("0").append(Integer.toHexString(b & 0xff));
            } else {
                sb.append(Integer.toHexString(b & 0xff));
            }
        }
        return sb.toString();
    }

    public static final String algorithm = "AES";
    // AES/CBC/NOPaddin
    // AES 默认模式
    // 使用CBC模式, 在初始化Cipher对象时, 需要增加参数, 初始化向量IV : IvParameterSpec iv = new
    // IvParameterSpec(key.getBytes());
    // NOPadding: 使用NOPadding模式时, 原文长度必须是8byte的整数倍
    public static final String transformation = "AES/CBC/NOPadding";
    public static final String key = "1234567812345671"; // must be 16 bytes long

    /**
     * AES 加密
     * @param data 需要加密的参数 (注意必须是 16 bytes 或 16 x n bytes)
     * */
    public static String Encrypt_AES(String data) throws Exception {
        // 获取Cipher
        Cipher cipher = Cipher.getInstance(transformation);
        // 生成密钥
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
        // 指定模式(加密)和密钥
        // 创建初始化向量
        IvParameterSpec iv = new IvParameterSpec(key.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
        // 加密
        byte[] bytes = cipher.doFinal(data.getBytes());
        // BASE64 编码
        return Encode_BASE64(bytes);
    }

    /**
     * AES 解密
     * @param data 需要解密的参数
     * */
    public static String Decrypt_AES(String data) throws Exception {
        // 获取Cipher
        Cipher cipher = Cipher.getInstance(transformation);
        // 生成密钥
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
        // 指定模式(解密)和密钥
        // 创建初始化向量
        IvParameterSpec iv = new IvParameterSpec(key.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
        // 解密
        byte[] bytes = cipher.doFinal(Decode_BASE64(data));

        return new String(bytes);
    }

}
