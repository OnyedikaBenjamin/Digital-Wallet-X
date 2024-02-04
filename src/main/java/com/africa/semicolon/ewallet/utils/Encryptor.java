package com.africa.semicolon.ewallet.utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class Encryptor {
    private static final String ALGO = "AES";
    private static final byte[] keyValue = new byte[] { 'T', 'h', 'i', 's', 'I', 's', 'A', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };
//    private static byte[] keyValue = System.getenv("ENCRYPTION_KEY").getBytes();


    public static String encrypt(String balance) throws Exception {

        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encValue = c.doFinal(balance.getBytes());
        String encryptedValue = Base64.getEncoder().encodeToString(encValue);
        return encryptedValue;
    }

    public static String decrypt(String encryptedBalance) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = Base64.getDecoder().decode(encryptedBalance);
        byte[] decValue = c.doFinal(decordedValue);
        return new String(decValue);
    }

    private static Key generateKey() throws Exception {
        return new SecretKeySpec(keyValue, ALGO);
    }
}
