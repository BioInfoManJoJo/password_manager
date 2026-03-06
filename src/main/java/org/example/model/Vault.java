package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Vault {

    private List<PasswordEntry> entries;

    public Vault() {
        this.entries = new ArrayList<>();
    }

    public List<PasswordEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<PasswordEntry> entries) {
        this.entries = entries;
    }

    public void addEntry(PasswordEntry entry) {
        entries.add(entry);
    }

    public void removeEntry(PasswordEntry entry) {
        entries.remove(entry);
    }
}