package com.codecool.dungeoncrawl.logic.util;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SaveGameModal {
    static String name;

    public static void display(Stage stage) {
        Stage modal = new Stage();
        modal.initOwner(stage);
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.setAlwaysOnTop(true);
        modal.setTitle("Save");
        modal.setMaxWidth(900);
        modal.setMaxHeight(300);
        modal.setMaximized(false);
        modal.setResizable(false);
        modal.setOpacity(0.8);

        TextField textField = new TextField();
        textField.setText("Name");
        textField.setFont(new Font("Pixeled Regular", 12));
        textField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                name = textField.getText();
                modal.close();
            }
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(textField);
        Scene scene = new Scene(borderPane);
        modal.setScene(scene);
        modal.showAndWait();
    }
}
