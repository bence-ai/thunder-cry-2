package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Game extends Application {
    GameMap tutorialMap = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            tutorialMap.getWidth() * Tiles.TILE_WIDTH,
            tutorialMap.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();

    Label healthLabel = new Label();
    Label mannaLabel = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane ui = new GridPane();
        ui.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(0), Insets.EMPTY)));
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(20));

        Label healthText = new Label("Health: ");
        healthText.setFont(new Font("Chalkduster", 20));
        ui.add(healthText, 0, 0);
        healthLabel.setFont(new Font("Chalkduster", 20));
        ui.add(healthLabel, 1, 0);
        Label mannaText = new Label("Manna: ");
        mannaText.setFont(new Font("Chalkduster", 20));
        ui.add(mannaText, 0, 1);
        mannaLabel.setFont(new Font("Chalkduster", 20));
        ui.add(mannaLabel, 1, 1);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setTop(ui);
        refresh();
    }

        private void onKeyPressed(KeyEvent keyEvent) {
        Cell neighbourCell = tutorialMap.getPlayer().getCell();
        switch (keyEvent.getCode()) {
            case UP:
                tutorialMap.getPlayer().move(0, -1);
                refresh();
                break;
            case DOWN:
                tutorialMap.getPlayer().move(0, 1);
                refresh();
                break;
            case LEFT:
                tutorialMap.getPlayer().move(-1, 0);
                refresh();
                break;
            case RIGHT:
                tutorialMap.getPlayer().move(1,0);
                refresh();
                break;
            case E:
                if (neighbourCell.getNeighbor(1,0).getType().equals(CellType.ITEM)) {

                }
        }
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < tutorialMap.getWidth(); x++) {
            for (int y = 0; y < tutorialMap.getHeight(); y++) {
                Cell cell = tutorialMap.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + tutorialMap.getPlayer().getHealth());
        healthLabel.setText("" + tutorialMap.getPlayer().getHealth());
    }
}