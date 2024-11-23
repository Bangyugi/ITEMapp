package com.bangvan.EMwebapp.util;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class AESutil {
    private static final String SECRET_KEY = "yMMY0BIKIPwqU2Ro2OsRDQ==";
    private static final String INIT_VECTOR = "x2wUB8Dj7ohaPhzLRBhgvQ==";


    public static SecretKeySpec generateKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
        return new SecretKeySpec(decodedKey, "AES");
    }

    public static IvParameterSpec generateIv() {
        byte[] decodedIV = Base64.getDecoder().decode(INIT_VECTOR);
        return new IvParameterSpec(decodedIV);
    }

    public static String encrypt
            (

                    String input,
                    SecretKey key,
                    IvParameterSpec ivParameterSpec
            )
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        if (input == null || input.length() == 0) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt
            (

                    String cipherText,
                    SecretKey key,
                    IvParameterSpec ivParameterSpec
            ) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if(cipherText == null || cipherText.length() == 0) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        byte[] plaintext = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(plaintext);
    }




}


