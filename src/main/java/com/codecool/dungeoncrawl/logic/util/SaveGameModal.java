package com.codecool.dungeoncrawl.logic.util;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

public class SaveGameModal {
    GameDatabaseManager dbManager;

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

    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }
}
