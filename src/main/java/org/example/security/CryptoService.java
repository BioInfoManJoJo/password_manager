package org.example.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.SecureRandom;

public class CryptoService {

    private static final String ALGO = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128;
    private static final int IV_LENGTH = 12;

    public byte[] encrypt(byte[] data, SecretKey key) throws Exception {

        byte[] iv = new byte[IV_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        Cipher cipher = Cipher.getInstance(ALGO);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);

        cipher.init(Cipher.ENCRYPT_MODE, key, spec);

        byte[] encrypted = cipher.doFinal(data);

        byte[] result = new byte[iv.length + encrypted.length];

        System.arraycopy(iv, 0, result, 0, iv.length);
        System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);

        return result;
    }

    public byte[] decrypt(byte[] encryptedData, SecretKey key) throws Exception {

        byte[] iv = new byte[IV_LENGTH];
        System.arraycopy(encryptedData, 0, iv, 0, iv.length);

        byte[] cipherText = new byte[encryptedData.length - iv.length];
        System.arraycopy(encryptedData, iv.length, cipherText, 0, cipherText.length);

        Cipher cipher = Cipher.getInstance(ALGO);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);

        cipher.init(Cipher.DECRYPT_MODE, key, spec);

        return cipher.doFinal(cipherText);
    }
}