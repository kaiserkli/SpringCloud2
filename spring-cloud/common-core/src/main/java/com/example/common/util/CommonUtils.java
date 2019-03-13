package com.example.common.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.servlet.http.HttpServletRequest;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.text.DecimalFormat;

public class CommonUtils {

    private enum DigestType {
        MD5("MD5")
        ,SHA("SHA")
        ,SHA256("SHA-256")
        ,SHA512("SHA-512");

        private String digest;

        private DigestType(String digest) {
            this.digest = digest;
        }

        public String getDigest() {
            return digest;
        }
    }

    /**
     * Encode a string using algorithm specified in web.xml and return the
     * resulting encrypted password. If exception, the plain credentials string
     * is returned
     *
     * @param password
     *            Password or other credentials to use in authenticating this
     *            username
     * @param digestType
     *            Algorithm used to do the digest
     *
     * @return encypted password based on the algorithm.
     */
    public static String encodePassword(String password, DigestType digestType) {
        byte[] unencodedPassword = password.getBytes();

        MessageDigest md = null;

        try {
            // first create an instance, given the provider
            md = MessageDigest.getInstance(digestType.digest);
        } catch (Exception e) {
            e.printStackTrace();
            return password;
        }

        md.reset();

        // call the update method one or more times
        // (useful when you don't know the size of your data, eg. stream)
        md.update(unencodedPassword);

        // now calculate the hash
        byte[] encodedPassword = md.digest();

        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < encodedPassword.length; i++) {
            if ((encodedPassword[i] & 0xff) < 0x10) {
                buf.append("0");
            }

            buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
        }

        return buf.toString();
    }


    /**
     * 获取客户端IP地址
     * @param request 客户端请求
     * @return String
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded == null || forwarded.length() == 0 || "unknown".equalsIgnoreCase(forwarded)) {
            forwarded = request.getHeader("Proxy-Client-IP");
        }
        if (forwarded == null || forwarded.length() == 0 || "unknown".equalsIgnoreCase(forwarded)) {
            forwarded = request.getHeader("WL-Proxy-Client-IP");
        }
        if (forwarded == null || forwarded.length() == 0 || "unknown".equalsIgnoreCase(forwarded)) {
            forwarded = request.getRemoteAddr();
        }

        String remoteAddr = "unknown";
        for (int i = 0; i < forwarded.split(",").length; i++) {
            if (!"unknown".equalsIgnoreCase(forwarded.split(",")[i])) {
                remoteAddr = forwarded.split(",")[i];
                break;
            }
        }

        return remoteAddr;
    }

    private static final int ITERATIONS_NUM = 50;

    /**
     * 数据加密方法
     * @param key 加密KEY
     * @param plainText 被加密数据
     * @return String
     * @throws Exception
     */
    public static String encrypt(String key, String plainText) throws Exception {
        byte[] salt = new byte[8];
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(key.getBytes());
        byte[] digest = md.digest();
        for (int i = 0; i < 8; i++) {
            salt[i] = digest[i];
        }
        PBEKeySpec pbeKeySpec = new PBEKeySpec(key.toCharArray());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey sk = skf.generateSecret(pbeKeySpec);
        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, ITERATIONS_NUM);
        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
        cipher.init(Cipher.ENCRYPT_MODE, sk, pbeParameterSpec);
        byte[] cipherText = cipher.doFinal(plainText.getBytes("UTF-8"));
        String saltValue = Base64.encodeBase64URLSafeString(salt);
        String cipherTextValue = Base64.encodeBase64URLSafeString(cipherText);

        return saltValue + cipherTextValue;
    }

    /**
     * 数据解密方法
     * @param key 加密KEY
     * @param encryptText 被解密数据
     * @return String
     * @throws Exception
     */
    public static String decrypt(String key, String encryptText) throws Exception {
        int saltLength = 11;

        String saltValue = encryptText.substring(0, saltLength);
        String cipherTextValue = encryptText.substring(saltLength, encryptText.length());
        byte[] salt = Base64.decodeBase64(saltValue.getBytes("UTF-8"));
        byte[] cipherText = Base64.decodeBase64(cipherTextValue.getBytes("UTF-8"));
        PBEKeySpec keySpec = new PBEKeySpec(key.toCharArray());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey sk = skf.generateSecret(keySpec);
        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, ITERATIONS_NUM);
        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
        cipher.init(Cipher.DECRYPT_MODE, sk, pbeParameterSpec);
        byte[] plainText = cipher.doFinal(cipherText);

        return new String(plainText, "UTF-8");
    }


    /**
     * 货币格式化(无货币符号)
     * @param account 金额
     * @return String
     */
    public static String getCurrencyFormat(double account) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(account);
    }

    /**
     * double转换成整数(向上取整)
     * @param account 金额
     * @return String
     */
    public static String getWholeNumberFormat(double account) {
        DecimalFormat decimalFormat = new DecimalFormat("0");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        return decimalFormat.format(account);
    }

    /**
     * double转换成1位小数(向上取整)
     * @param account 金额
     * @return String
     */
    public static String getADecimalFormat(double account) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        return decimalFormat.format(account);
    }
}
