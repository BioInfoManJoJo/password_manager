package org.example.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Vault;

import java.io.File;
import java.io.IOException;

public class VaultRepository {

    private static final String FILE_NAME = "vault.json";
    private final ObjectMapper mapper = new ObjectMapper();

    public void save(Vault vault) throws IOException {
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(FILE_NAME), vault);
    }

    public Vault load() throws IOException {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return new Vault();
        }

        return mapper.readValue(file, Vault.class);
    }
}