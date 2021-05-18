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
import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;

public class Main extends Application {
    Stage stage = new Stage();
    Font BUTTON_NORMAL = new Font("Pixeled Regular", 25);
    Font BUTTON_LARGE = new Font("Pixeled Regular", 45);
    Font LOGO_FONT = new Font("Vehicle Breaks Down Regular", 150);
    GameMap map;
    Canvas canvas;
    GraphicsContext context;
    Label healthLabel = new Label();
    GameDatabaseManager dbManager;

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
        setupDbManager();
    }
    private void loadGame(MouseEvent mouseEvent) {

        setupDbManager();
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);
        scene.setOnKeyReleased(this::onKeyReleased);

        stage.setTitle("Dungeon Crawl");
        stage.show();
    }

    private void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
        if (exitCombinationMac.match(keyEvent)
                || exitCombinationWin.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            exit();
        }
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1, 0);
                refresh();
                break;
            case S:
                Player player = map.getPlayer();
                dbManager.savePlayer(player);
                break;
        }
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
    }

    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

    private void exit() {
        try {
            stop();
        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }
}
