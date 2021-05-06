package com.codecool.dungeoncrawl;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Reflection;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.print.attribute.standard.Media;
import java.io.File;


public class Main extends Application {
    Stage stage = new Stage();
    Font BUTTON_NORMAL = new Font("Pixeled Regular", 25);
    Font BUTTON_LARGE = new Font("Pixeled Regular", 45);
    Font LOGO_FONT = new Font("Vehicle Breaks Down Regular", 150);


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setResizable(false);
        stage.setTitle("ThunderCry");
        stage.setWidth(1300);
        stage.setHeight(700);


        Text thunderCry = new Text();
        thunderCry.setText("ThunderCry");
        thunderCry.setFill(Color.WHITE);
        thunderCry.setFont(LOGO_FONT);
        Button newGame = buttonFactory("New Game");
        Button loadGame = buttonFactory("Load Game");
        Button exit = buttonFactory("Exit");
        GridPane buttons = new GridPane();
        buttons.add(newGame,0,0);
        buttons.add(loadGame,0,1);
        buttons.setAlignment(Pos.CENTER);
        exit.setFont(BUTTON_NORMAL);

        BorderPane borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), Insets.EMPTY)));
        borderPane.setTop(thunderCry);
        borderPane.setCenter(buttons);
        borderPane.setBottom(exit);
        borderPane.setAlignment(thunderCry, Pos.CENTER);
        borderPane.setAlignment(exit, Pos.CENTER);

        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add("Loader/button.css");
        stage.setScene(scene);

        FadeTransition fade = new FadeTransition(Duration.seconds(3), newGame);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        exit.setOnMouseClicked(this::exit);
        newGame.setOnMouseClicked(this::newGame);
        loadGame.setOnMouseClicked(this::loadGame);
        loadGame.setDisable(true);
        stage.show();
        stage.setScene(scene);
    }

    private Button buttonFactory(String text) {
        Button button = new Button();

        button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(0), Insets.EMPTY)));
        button.setText(text);
        button.setPrefWidth(1000);
        button.setTextFill(Color.WHITE);
        button.setFont(BUTTON_LARGE);
        button.setAlignment(Pos.CENTER);
        button.setPadding(new Insets(15,0,0,0));
        Reflection reflection = new Reflection();
        reflection.setTopOffset(-90);
        button.setEffect(reflection);
        button.setCursor(Cursor.HAND);
        return button;
    }

    private void exit(MouseEvent mouseEvent) {
        System.exit(0);
    }

    private void newGame(MouseEvent mouseEvent) {
        Game game = new Game(stage, "Hubb");
        game.loader();
    }
    private void loadGame(MouseEvent mouseEvent) {

    }
}
