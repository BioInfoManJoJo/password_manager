package org.example.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.example.model.PasswordEntry;
import org.example.model.Vault;
import org.example.storage.EncryptedVaultRepository;

public class VaultView {

    private TableView<PasswordEntry> table = new TableView<>();

    private Vault vault;
    private EncryptedVaultRepository repository;
    private char[] masterPassword;

    public VaultView(Vault vault, char[] masterPassword) {
        this.vault = vault;
        this.masterPassword = masterPassword;
        this.repository = new EncryptedVaultRepository();
    }

    public BorderPane createView() {

        TableColumn<PasswordEntry, String> siteCol = new TableColumn<>("Site");
        siteCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getSite()));

        TableColumn<PasswordEntry, String> userCol = new TableColumn<>("Username");
        userCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getUsername()));

        TableColumn<PasswordEntry, Void> passCol = new TableColumn<>("Password");

        passCol.setCellFactory(col -> new TableCell<>() {

            private final Button revealBtn = new Button("Reveal");
            private final Button copyBtn = new Button("Copy");
            private final Label hidden = new Label("••••••");

            private final HBox box = new HBox(5, hidden, revealBtn, copyBtn);

            {
                revealBtn.setOnAction(e -> {

                    PasswordEntry entry = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Password");
                    alert.setContentText(entry.getPassword());
                    alert.showAndWait();

                });

                copyBtn.setOnAction(e -> {

                    PasswordEntry entry = getTableView().getItems().get(getIndex());

                    javafx.scene.input.Clipboard clipboard =
                            javafx.scene.input.Clipboard.getSystemClipboard();

                    javafx.scene.input.ClipboardContent content =
                            new javafx.scene.input.ClipboardContent();

                    content.putString(entry.getPassword());
                    clipboard.setContent(content);

                    new Thread(() -> {
                        try {
                            Thread.sleep(30000);

                            javafx.scene.input.ClipboardContent clear =
                                    new javafx.scene.input.ClipboardContent();

                            clear.putString("");
                            clipboard.setContent(clear);

                        } catch (Exception ignored) {}
                    }).start();

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {

                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(box);
                }
            }
        });

        table.getColumns().addAll(siteCol, userCol, passCol);

        ObservableList<PasswordEntry> data =
                FXCollections.observableArrayList(vault.getEntries());

        table.setItems(data);

        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");

        addButton.setOnAction(e -> {

            AddPasswordDialog dialog = new AddPasswordDialog();

            dialog.showDialog().ifPresent(entry -> {

                vault.addEntry(entry);
                table.getItems().add(entry);

                try {
                    repository.save(vault, masterPassword);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            });

        });

        deleteButton.setOnAction(e -> {

            PasswordEntry selected = table.getSelectionModel().getSelectedItem();

            if (selected != null) {

                vault.removeEntry(selected);
                table.getItems().remove(selected);

                try {
                    repository.save(vault, masterPassword);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        });

        ToolBar toolbar = new ToolBar(addButton, deleteButton);

        BorderPane root = new BorderPane();
        root.setTop(toolbar);
        root.setCenter(table);

        return root;
    }
}