package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Main extends Application {
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label mannaLabel = new Label();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent page = FXMLLoader.<Parent>load(MainWindowController.class.getResource("resources/Loader/main.fxml").toExternalForm());

        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
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
//        borderPane.setBackground(new Background(new BackgroundImage(new Image("/tiles.png"),BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("ThunderCry 2");
        primaryStage.show();
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
            case T:


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
        healthLabel.setText("" + map.getPlayer().getHealth());
    }
}
