package com.codecool.dungeoncrawl;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Loader extends Application {
    private Stage primaryStage;

    public static void main(String[] args) {
        Application.launch(Loader.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        Pane root = FXMLLoader.load(getClass().getResource("/Loader/main.fxml"));
        Scene scene = new Scene(root, 600, 700);
        primaryStage.setTitle("Character Creation");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @FXML
    public void newGame() {
    }

    @FXML
    public void exitLoader() {
        System.exit(0);
    }
}
