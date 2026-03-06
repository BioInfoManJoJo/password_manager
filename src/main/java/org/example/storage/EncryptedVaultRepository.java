package org.example.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Vault;
import org.example.security.CryptoService;
import org.example.security.KeyDerivation;

import javax.crypto.SecretKey;
import java.io.File;
import java.nio.file.Files;

public class EncryptedVaultRepository {

    private static final String FILE_NAME = "vault.dat";

    private final ObjectMapper mapper = new ObjectMapper();
    private final CryptoService crypto = new CryptoService();
    private final KeyDerivation keyDerivation = new KeyDerivation();

    public void save(Vault vault, char[] password) throws Exception {

        byte[] salt = keyDerivation.generateSalt();

        SecretKey key = keyDerivation.deriveKey(password, salt);

        byte[] json = mapper.writeValueAsBytes(vault);

        byte[] encrypted = crypto.encrypt(json, key);

        byte[] result = new byte[salt.length + encrypted.length];

        System.arraycopy(salt, 0, result, 0, salt.length);
        System.arraycopy(encrypted, 0, result, salt.length, encrypted.length);

        Files.write(new File(FILE_NAME).toPath(), result);
    }

    public Vault load(char[] password) throws Exception {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return new Vault();
        }

        byte[] data = Files.readAllBytes(file.toPath());

        byte[] salt = new byte[16];
        System.arraycopy(data, 0, salt, 0, 16);

        byte[] encrypted = new byte[data.length - 16];
        System.arraycopy(data, 16, encrypted, 0, encrypted.length);

        SecretKey key = keyDerivation.deriveKey(password, salt);

        byte[] json = crypto.decrypt(encrypted, key);

        return mapper.readValue(json, Vault.class);
    }
}