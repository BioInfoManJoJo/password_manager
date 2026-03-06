package org.example;

import org.example.model.PasswordEntry;
import org.example.model.Vault;
import org.example.storage.EncryptedVaultRepository;

public class Main {

    public static void main(String[] args) throws Exception {

        EncryptedVaultRepository repo = new EncryptedVaultRepository();

        char[] password = "master123".toCharArray();

        Vault vault = repo.load(password);

        vault.addEntry(new PasswordEntry(
                "github.com",
                "user123",
                "mypassword",
                "2FA enabled"
        ));

        repo.save(vault, password);

        System.out.println("Entries: " + vault.getEntries().size());
    }
}