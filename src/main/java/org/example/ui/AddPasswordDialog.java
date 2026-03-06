package org.example.ui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.example.model.PasswordEntry;
import org.example.security.PasswordGenerator;

import java.util.Optional;

public class AddPasswordDialog {

    public Optional<PasswordEntry> showDialog() {

        Dialog<PasswordEntry> dialog = new Dialog<>();
        dialog.setTitle("Add Password");

        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        TextField siteField = new TextField();
        TextField userField = new TextField();
        PasswordField passField = new PasswordField();
        TextField notesField = new TextField();

        Button generateButton = new Button("Generate");

        PasswordGenerator generator = new PasswordGenerator();

        generateButton.setOnAction(e -> {
            String generated = generator.generateStrongPassword();
            passField.setText(generated);
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Site"), 0, 0);
        grid.add(siteField, 1, 0);

        grid.add(new Label("Username"), 0, 1);
        grid.add(userField, 1, 1);

        grid.add(new Label("Password"), 0, 2);
        grid.add(passField, 1, 2);
        grid.add(generateButton, 2, 2);

        grid.add(new Label("Notes"), 0, 3);
        grid.add(notesField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button -> {
            if (button == saveButton) {
                return new PasswordEntry(
                        siteField.getText(),
                        userField.getText(),
                        passField.getText(),
                        notesField.getText()
                );
            }
            return null;
        });

        return dialog.showAndWait();
    }
}