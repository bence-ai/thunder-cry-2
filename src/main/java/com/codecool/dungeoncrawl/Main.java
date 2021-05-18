package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.PlayerAvatar;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Reflection;
import javafx.scene.image.ImageView;
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
    BorderPane borderPane = new BorderPane();
    Font BUTTON_SMALL = new Font("Pixeled Regular", 18);
    Font BUTTON_NORMAL = new Font("Pixeled Regular", 25);
    Font BUTTON_LARGE = new Font("Pixeled Regular", 45);
    Font LOGO_FONT = new Font("Vehicle Breaks Down Regular", 150);

    PlayerAvatar playerAvatar = PlayerAvatar.BLUE_BOY;
    String playerName;
    Label noNameLabel = new Label();
    GameMap map;
    Canvas canvas;
    GraphicsContext context;
    Label healthLabel = new Label();
    GameDatabaseManager dbManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
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

        borderPane.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), Insets.EMPTY)));
        borderPane.setTop(thunderCry);
        borderPane.setCenter(buttons);
        borderPane.setBottom(exit);
        borderPane.setAlignment(thunderCry, Pos.CENTER);
        borderPane.setAlignment(exit, Pos.CENTER);

        if (borderPane.getScene() == null) {
            Scene scene = new Scene(borderPane);
            scene.getStylesheets().add("Loader/button.css");
            stage.setScene(scene);
        }

        exit.setOnMouseClicked(this::exit);
        newGame.setOnMouseClicked(this::newGame);
        loadGame.setOnMouseClicked(this::loadGame);
        loadGame.setDisable(true);

        stage.show();
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
        stage.setTitle("Character creation");
        TextField nameField = new TextField();
        final ToggleGroup genderGroup = new ToggleGroup();
        final ToggleGroup colorGroup = new ToggleGroup();
        ImageView imageView = new ImageView();

        setCharacterSelect(nameField, genderGroup, colorGroup, imageView);

        Button back = buttonFactory("Back");
        back.setFont(BUTTON_NORMAL);
        back.setOnMouseClicked(this::back);

        Button save = buttonFactory("Save Character");
        save.setFont(BUTTON_NORMAL);
        save.setOnMouseClicked(this::save);

        HBox buttons = new HBox();
        buttons.getChildren().addAll(back, save);
        buttons.setPadding(new Insets(5,10,10,0));

        borderPane.setBottom(buttons);

        genderGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                changeColor(genderGroup, colorGroup, imageView);
            }
        });

        colorGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                changeColor(genderGroup, colorGroup, imageView);
            }
        });

        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            playerName = newValue;
        });



        Game game = new Game(stage, "Hubb");
        game.loader();

    }

    private void changeColor(ToggleGroup genderGroup, ToggleGroup colorGroup, ImageView imageView) {
        if ("male".equals(genderGroup.getSelectedToggle().getUserData())) {
            if ("blue".equals(colorGroup.getSelectedToggle().getUserData())) {
                imageView.setImage(PlayerAvatar.BLUE_BOY.getPlayerAvatar());
                playerAvatar = PlayerAvatar.BLUE_BOY;
            } else if ("green".equals(colorGroup.getSelectedToggle().getUserData())) {
                imageView.setImage(PlayerAvatar.GREEN_BOY.getPlayerAvatar());
                playerAvatar = PlayerAvatar.GREEN_BOY;
            } else if ("orange".equals(colorGroup.getSelectedToggle().getUserData())) {
                imageView.setImage(PlayerAvatar.ORANGE_BOY.getPlayerAvatar());
                playerAvatar = PlayerAvatar.ORANGE_BOY;
            } else if ("brown".equals(colorGroup.getSelectedToggle().getUserData())) {
                imageView.setImage(PlayerAvatar.BROWN_BOY.getPlayerAvatar());
                playerAvatar = PlayerAvatar.BROWN_BOY;
            }
        } else if ("female".equals(genderGroup.getSelectedToggle().getUserData())) {
            if ("blue".equals(colorGroup.getSelectedToggle().getUserData())) {
                imageView.setImage(PlayerAvatar.BLUE_GIRL.getPlayerAvatar());
                playerAvatar = PlayerAvatar.BLUE_GIRL;
            } else if ("green".equals(colorGroup.getSelectedToggle().getUserData())) {
                imageView.setImage(PlayerAvatar.GREEN_GIRL.getPlayerAvatar());
                playerAvatar = PlayerAvatar.GREEN_GIRL;
            } else if ("orange".equals(colorGroup.getSelectedToggle().getUserData())) {
                imageView.setImage(PlayerAvatar.PINK_GIRL.getPlayerAvatar());
                playerAvatar = PlayerAvatar.PINK_GIRL;
            } else if ("brown".equals(colorGroup.getSelectedToggle().getUserData())) {
                imageView.setImage(PlayerAvatar.BROWN_GIRL.getPlayerAvatar());
                playerAvatar = PlayerAvatar.BROWN_GIRL;
            }
        }
    }


    private void setCharacterSelect(TextField nameField, ToggleGroup genderGroup, ToggleGroup colorGroup, ImageView imageView) {
        BorderPane characterAvatar = new BorderPane();
        imageView.setImage(PlayerAvatar.BLUE_BOY.getPlayerAvatar());
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
        characterAvatar.setCenter(imageView);
        characterAvatar.setPadding(new Insets(0,0,0,70));
        GridPane playerInfo = new GridPane();
        Label nameLabel = new Label();
        nameLabel.setText("Choose your name: ");
        nameLabel.setFont(BUTTON_SMALL);
        nameField.setFont(BUTTON_SMALL);
        nameLabel.setTextFill(Color.AQUA);
        noNameLabel.setFont(BUTTON_SMALL);
        noNameLabel.setTextFill(Color.RED);
        playerInfo.add(nameLabel, 0,0);
        playerInfo.add(nameField, 1,0);
        playerInfo.add(noNameLabel, 2,0);


        Label genderLabel = new Label();
        Label colorLabel = new Label();
        genderLabel.setText("Choose your gender: ");
        colorLabel.setText("Choose color: ");
        genderLabel.setFont(BUTTON_SMALL);
        colorLabel.setFont(BUTTON_SMALL);
        colorLabel.setTextFill(Color.AQUA);
        genderLabel.setTextFill(Color.AQUA);
        RadioButton male = new RadioButton();
        RadioButton female = new RadioButton();
        male.setUserData("male");
        female.setUserData("female");
        male.setFont(BUTTON_SMALL);
        female.setFont(BUTTON_SMALL);
        male.setTextFill(Color.WHITE);
        female.setTextFill(Color.WHITE);
        male.setText("Male");
        female.setText("Female");
        male.setSelected(true);
        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);
        RadioButton blue = new RadioButton();
        RadioButton green = new RadioButton();
        RadioButton orangePink = new RadioButton();
        RadioButton brown = new RadioButton();
        blue.setSelected(true);
        blue.setUserData("blue");
        green.setUserData("green");
        orangePink.setUserData("orange");
        brown.setUserData("brown");
        blue.setFont(BUTTON_SMALL);
        green.setFont(BUTTON_SMALL);
        orangePink.setFont(BUTTON_SMALL);
        brown.setFont(BUTTON_SMALL);
        blue.setTextFill(Color.WHITE);
        green.setTextFill(Color.WHITE);
        orangePink.setTextFill(Color.WHITE);
        brown.setTextFill(Color.WHITE);
        blue.setText("Blue");
        green.setText("Green");
        orangePink.setText("Orange/Pink");
        brown.setText("Brown");
        blue.setToggleGroup(colorGroup);
        green.setToggleGroup(colorGroup);
        orangePink.setToggleGroup(colorGroup);
        brown.setToggleGroup(colorGroup);
        playerInfo.add(genderLabel, 0,1);
        playerInfo.add(male, 1,1);
        playerInfo.add(female, 2,1);
        playerInfo.add(colorLabel,0,2);
        playerInfo.add(blue, 1,2);
        playerInfo.add(green, 2,2);
        playerInfo.add(brown, 1,3);
        playerInfo.add(orangePink, 2,3);
        playerInfo.setPadding(new Insets(70,10,10,40));
        HBox character = new HBox();
        character.getChildren().addAll(characterAvatar, playerInfo);

        borderPane.setCenter(character);
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

    private void back(MouseEvent mouseEvent) {
        try {
            this.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save(MouseEvent mouseEvent) {
        int minNameCharacter = 4;
        if (playerName == null || playerName.length() >= minNameCharacter) {
            noNameLabel.setText("Ups, missed something?!");
            return;
        }

        Player player = new Player(null, playerName, playerAvatar);
        Game game = new Game(stage, player);
        game.loader();
    }
}
