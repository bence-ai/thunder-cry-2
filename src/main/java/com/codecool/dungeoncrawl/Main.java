package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.PlayerAvatar;
import com.codecool.dungeoncrawl.logic.items.Weapon;
import com.codecool.dungeoncrawl.model.GameState;
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

import java.sql.SQLException;
import java.util.List;

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
    GameDatabaseManager dbManager;
    GameState selectedGameState;

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
            noNameLabel.setText("");
        });
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
        List<GameState> gameStates = dbManager.getGameStateDao().getAll();
        initLoadGame(gameStates);
    }

    private void initLoadGame(List<GameState> gameStates) {
        Button back = new Button();
        Button load = new Button();
        ListView<GameState> listView = new ListView<>();

        listView.getItems().addAll(gameStates);
        listView.setOnMouseClicked(event -> {
            selectedGameState = listView.getSelectionModel().getSelectedItem();
            load.setDisable(false);
        });
        load.setDisable(true);
        back.setText("Back");
        load.setText("Load Selected");
        back.setFont(BUTTON_NORMAL);
        load.setFont(BUTTON_NORMAL);
        back.setOnMouseClicked(this::back);
        load.setOnMouseClicked(this::setUpAndPlay);
        HBox buttons = new HBox();
        buttons.getChildren().addAll(back, load);
        buttons.setPadding(new Insets(5,10,10,0));

        borderPane.setCenter(listView);
        borderPane.setBottom(buttons);
    }

    private void setUpAndPlay(MouseEvent mouseEvent) {
        int level = selectedGameState.getCurrentMap();
        String playerName = selectedGameState.getPlayer().getPlayerName();
        int hp = selectedGameState.getPlayer().getHp();
        int mp = selectedGameState.getPlayer().getMp();
        int defense = selectedGameState.getPlayer().getDefense();
        String weaponName = selectedGameState.getPlayer().getWeapon();
        String avatarName = selectedGameState.getPlayer().getAvatar();
        PlayerAvatar avatar = PlayerAvatar.valueOf(avatarName);

        Player player = new Player(null, playerName, avatar);
        player.setMapLevel(level);
        player.setHealth(hp);
        player.setManaPoint(mp);
        player.setDefense(defense);
        try {
            Class<?> clazz = Class.forName(weaponName);
            Weapon weapon = (Weapon) clazz.newInstance();
            player.setWeapon(weapon);
            System.out.println("Done: " + weapon);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Game game = new Game(stage, player);
        game.play(level);
    }

    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

    private void exit(MouseEvent mouseEvent) {
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
        if (playerName == null || playerName.length() <= minNameCharacter) {
            noNameLabel.setText("Ups, missed something?!");
            return;
        }

        Player player = new Player(null, playerName, playerAvatar);
        Game game = new Game(stage, player);
        game.loader();
    }
}
