package org.example.app;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.model.Vault;
import org.example.storage.EncryptedVaultRepository;
import org.example.ui.VaultView;

public class PasswordManagerApp extends Application {

    @Override
    public void start(Stage stage) {

        Label title = new Label("Password Manager");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Master Password");

        Button unlockButton = new Button("Unlock Vault");

        VBox login = new VBox(10, title, passwordField, unlockButton);
        login.setAlignment(Pos.CENTER);

        Scene scene = new Scene(login, 350, 200);

        stage.setTitle("Password Manager");
        stage.setScene(scene);
        stage.show();

        unlockButton.setOnAction(e -> {

            try {

                char[] password = passwordField.getText().toCharArray();

                EncryptedVaultRepository repo = new EncryptedVaultRepository();

                Vault vault = repo.load(password);

                VaultView view = new VaultView(vault, password);

                Scene vaultScene = new Scene(view.createView(), 600, 400);

                stage.setScene(vaultScene);

            } catch (Exception ex) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Wrong password or corrupted vault");
                alert.showAndWait();

            }

        });
    }

    public static void main(String[] args) {
        launch();
    }
}