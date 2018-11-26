package com.sinocall.phonerecordera.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DESUtils {
    // 只针对密码做的DES

    private static byte[] iv = {2, 0, 1, 5, 8, 8, 8, 8};

    public static String encryptDES(String encryptString, String encryptKey)
            throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        return toHexString(encryptedData);
    }

    public static String decryptDES(String decryptString, String decryptKey)
            throws Exception {
        byte[] byteMi = fromHexString(decryptString);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte [] decryptedData = cipher.doFinal(byteMi);
        return new String(decryptedData);
    }

    public static byte[] fromHexString(String text) {
        text = text.trim();
        if (text.length() % 2 != 0) {
            text = "0" + text;
        }
        int resLen = text.length() / 2;
        int loNibble, hiNibble;
        byte[] res = new byte[resLen];
        for (int i = 0; i < resLen; i++) {
            int j = i << 1;
            hiNibble = charToNibble(text.charAt(j));
            loNibble = charToNibble(text.charAt(j + 1));
            if (loNibble == -1 || hiNibble == -1) {
                return null;
            }
            res[i] = (byte) (hiNibble << 4 | loNibble);
        }
        return res;
    }

    public static String toHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    private static final int charToNibble(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        } else if (c >= 'a' && c <= 'f') {
            return 0xa + (c - 'a');
        } else if (c >= 'A' && c <= 'F') {
            return 0xA + (c - 'A');
        } else {
            return -1;
        }
    }
}
