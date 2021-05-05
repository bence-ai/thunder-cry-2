package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Battle;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.CharacterAvatar;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Game {
    Font defaultFont = new Font("Pixeled Regular", 12);

    private Stage stage;
    private BorderPane borderPane;
    Battle battle;
    GameMap map;
    GraphicsContext context;
    Canvas canvas;

    Label name = new Label();
    Label healthLabel = new Label();
    Label mannaLabel = new Label();
    Label enemyHealthLabel = new Label();
    Label enemyMannaLabel = new Label();
    Label enemyName = new Label();

    public Game(Stage stage, String name) {
        this.stage = stage;
        this.name.setText(name);
    }

    public void play() {
        BorderPane toolbar = setToolbar();

        canvas = tutorial();
        context  = canvas.getGraphicsContext2D();

        borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setTop(toolbar);



        Scene scene = new Scene(borderPane);
        scene.setOnKeyPressed(this::onKeyPressed);
        refresh();
        stage.setScene(scene);
        stage.setFullScreen(true);

        battle = new Battle(scene, toolbar);
        battle.fight(map.getPlayer(), map.getPlayer());
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
                map.getPlayer().move(1,0);
                refresh();
                break;
            }
    }

    private BorderPane setToolbar() {
        BorderPane toolbar = new BorderPane();
        GridPane characterInfo = new GridPane();
        characterInfo.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, new CornerRadii(0), Insets.EMPTY)));
        characterInfo.setPadding(new Insets(10));

        Image avatarImage = CharacterAvatar.BLUE_BOY.getCharacterAvatar();
        BorderPane playerAvatar = new BorderPane();
        playerAvatar.setCenter(new ImageView(avatarImage));
        playerAvatar.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, new CornerRadii(0), Insets.EMPTY)));
        BorderPane enemyAvatar = new BorderPane();

        Label nameText = new Label("Name: ");
        nameText.setFont(defaultFont);
        characterInfo.add(nameText, 0,0);
        name.setFont(defaultFont);
        characterInfo.add(name, 1,0);
        Label healthText = new Label("Health: ");
        healthText.setFont(defaultFont);
        characterInfo.add(healthText, 0, 1);
        healthLabel.setFont(defaultFont);
        characterInfo.add(healthLabel, 1, 1);
        Label mannaText = new Label("Manna: ");
        mannaText.setFont(defaultFont);
        characterInfo.add(mannaText, 0, 2);
        mannaLabel.setFont(defaultFont);
        characterInfo.add(mannaLabel, 1, 2);

        toolbar.setStyle("-fx-border-color : black; -fx-border-width : 5 0 ");
        toolbar.setLeft(playerAvatar);
        toolbar.setCenter(characterInfo);
        toolbar.setRight(enemyAvatar);
        toolbar.setMaxWidth(1920);
        return toolbar;
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
        mannaLabel.setText("" + map.getPlayer().getHealth());
    }

    private Canvas tutorial() {
        map = MapLoader.loadMap("tutorial");
        canvas = new Canvas(
                map.getWidth() * Tiles.TILE_WIDTH,
                map.getHeight() * Tiles.TILE_WIDTH);

        return canvas;
    }
}
