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
    public static String saveName;

    public static void display(Stage stage) {
        GameDatabaseManager dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }

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
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(textField);
        Scene scene = new Scene(borderPane);

        textField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                saveName = textField.getText();
                modal.close();
            }
        });
        modal.setScene(scene);
        modal.showAndWait();
    }
}
