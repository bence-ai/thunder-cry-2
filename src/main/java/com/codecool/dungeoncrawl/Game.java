package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.dao.JsonManager;
import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.BareHand;
import com.codecool.dungeoncrawl.logic.items.ItemType;
import com.codecool.dungeoncrawl.logic.magic.Spell;
import com.codecool.dungeoncrawl.logic.util.SaveGameModal;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;

public class Game {
    Background TOOLBOX_FILL_COLOR = new Background(new BackgroundFill(Color.DIMGRAY, new CornerRadii(0), Insets.EMPTY));
    Background BLACK_FILL_COLOR = new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), Insets.EMPTY));

    Font LOGO_FONT = new Font("Vehicle Breaks Down Regular", 180);
    Font SMALL_FONT = new Font("Pixeled Regular", 12);
    Font MEDIUM_FONT = new Font("Pixeled Regular", 15);
    Font LARGE_FONT = new Font("Pixeled Regular", 25);
    Font EXTRA_LARGE_FONT = new Font("Pixeled Regular", 65);

    private final Stage stage;
    private BorderPane borderPane;
    Battle battle;
    GameMap map;
    GraphicsContext context;
    Canvas canvas;
    Scene scene;
    BorderPane toolbar;

    Player player;
    Label name = new Label();
    Label healthLabel = new Label();
    Label mannaLabel = new Label();
    Label defenseLabel = new Label();
    Label enemyName = new Label();
    Label enemyHealthLabel = new Label();
    Label enemyMannaLabel = new Label();
    Label enemyDefenseLabel = new Label();
    Label infoLabel = new Label();
    BorderPane weaponAvatar = new BorderPane();
    BorderPane enemyAvatar = new BorderPane();

    GameDatabaseManager dbManager;

    public Game(Stage stage, Player player) {
        this.stage = stage;
        this.player = player;
    }

    public void loader() {
        VBox loaderPage = new VBox();
        loaderPage.setBackground(BLACK_FILL_COLOR);

        borderPane = new BorderPane();

        Label logo = new Label("ThunderCry");
        logo.setFont(LOGO_FONT);
        logo.setTextFill(Color.WHITE);
        loaderPage.getChildren().add(logo);

        Label title = new Label("and the lost Island 2");
        title.setFont(EXTRA_LARGE_FONT);
        title.setTextFill(Color.BLACK);
        title.setPadding(new Insets(-70,0,0,0));
        FadeTransition titleFadeIn = new FadeTransition(Duration.seconds(10), title);
        titleFadeIn.setFromValue(0);
        titleFadeIn.setToValue(1);

        loaderPage.getChildren().add(title);
        borderPane.setCenter(loaderPage);
        loaderPage.setAlignment(Pos.CENTER);

        Label pressButton = new Label("Press any button to start...");
        pressButton.setTextFill(Color.BLACK);
        pressButton.setFont(MEDIUM_FONT);
        FadeTransition pressAnimation = new FadeTransition(Duration.seconds(1), pressButton);
        pressAnimation.setFromValue(0);
        pressAnimation.setToValue(1);
        pressAnimation.setAutoReverse(true);
        pressAnimation.setCycleCount(50);
        borderPane.setBottom(pressButton);

        borderPane.setAlignment(pressButton, Pos.CENTER);
        borderPane.setCursor(Cursor.NONE);
        this.scene = new Scene(borderPane);
        scene.setOnKeyPressed(this::playGame);

        stage.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_ANY));
        stage.setFullScreenExitHint("(Ctr + Alt + X) -> Exit Fullscreen Mode ????");
        stage.setScene(scene);
        stage.setFullScreen(true);

        titleFadeIn.play();
        title.setTextFill(Color.AQUA);
        pressAnimation.play();
    }

    private void playGame(KeyEvent keyEvent) {
        this.play(0);
    }

    public void play(int level) {
        switch (level) {
            case 0:
                canvas = tutorial();
                context = canvas.getGraphicsContext2D();
                break;
            case 1:
            case 2:
                canvas = level2();
                context = canvas.getGraphicsContext2D();
                break;
        }

        this.toolbar = setToolbar();
        if (borderPane == null) {
            borderPane = new BorderPane();
            scene = new Scene(borderPane);
            stage.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_ANY));
            stage.setFullScreenExitHint("(Ctr + Alt + X) -> Exit Fullscreen Mode ????");
            stage.setScene(scene);
            stage.setFullScreen(true);
        }
        borderPane.setCenter(canvas);
        borderPane.setTop(toolbar);
        borderPane.setBottom(null);

        scene.setOnKeyPressed(this::onKeyPressed);
        refresh();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                update();
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                update();
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                update();
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1, 0);
                update();
                refresh();
                break;
            case F: // Start a fight with nearby enemy
                if (map.getPlayer().isThereEnemy()) {
                    infoLabel.setText("Prepare for the battle and choose an action!");
                    battle = new Battle(scene, infoLabel, healthLabel, mannaLabel, enemyHealthLabel, enemyMannaLabel, map);
                    battle.fight(map.getPlayer(), map.getPlayer().getEnemy());
                }
                break;
            case E: // Pick-up items
                checkForItem();
                refresh();
                break;
        }

        final KeyCombination save = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
        if (save.match(keyEvent)) {
            SaveGameModal.display(stage);
            PlayerModel playerModel = new PlayerModel(map.getPlayer());
            GameState gameState = new GameState(SaveGameModal.saveName, map.getPlayer().getMapLevel(), new Date(System.currentTimeMillis()), playerModel);
            setupDbManager();
            dbManager.getGameStateDao().add(gameState);
        }

        final KeyCombination export = new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN);
        if (export.match(keyEvent)) {
            JsonManager json = new JsonManager();
            PlayerModel playerModel = new PlayerModel(map.getPlayer());
            GameState gameState = new GameState(null, map.getPlayer().getMapLevel(),new Date(System.currentTimeMillis()),playerModel);
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setInitialFileName("dungeon_crawl.json");
            borderPane.setCursor(Cursor.DEFAULT);
            File savingFile = fileChooser.showSaveDialog(stage);
            borderPane.setCursor(Cursor.NONE);
            json.saveToProjectFile(savingFile,gameState);
            refresh();
        }


    }

    /**
     * checking if there is an item at the current position, if so then picking it up
     */
    private void checkForItem() {
        if (map.getPlayer().getCell().getItem() != null) {
            switch (map.getPlayer().getCell().getItem().getType()) {
                case WEAPON:
                    map.getPlayer().setWeapon(map.getPlayer().getCell().getWeapon());
                    map.getPlayer().getCell().setItem(null);
                    refresh();
                    break;
                case ARMOUR:
                    map.getPlayer().setDefense(map.getPlayer().getCell().getItem().getProperty());
                    map.getPlayer().getCell().setItem(null);
                    refresh();
                    break;
                case POTION:
                    map.getPlayer().healHP(map.getPlayer().getCell().getItem().getProperty());
                    map.getPlayer().getCell().setItem(null);
                    refresh();
                    break;
                case ELIXIR:
                    map.getPlayer().restoreMP(map.getPlayer().getCell().getItem().getProperty());
                    map.getPlayer().getCell().setItem(null);
                    refresh();
                    break;
                default:
                    map.getPlayer().pickUpItem(map.getPlayer().getCell().getItem());
                    map.getPlayer().getCell().setItem(null);
                    refresh();
            }
        }
    }

    private BorderPane setToolbar() {
        BorderPane toolbar = new BorderPane();
        GridPane characterInfo = new GridPane();
        characterInfo.setBackground(TOOLBOX_FILL_COLOR);
        characterInfo.setPadding(new Insets(10));
        GridPane enemyInfo = new GridPane();
        enemyInfo.setBackground(TOOLBOX_FILL_COLOR);
        enemyInfo.setPadding(new Insets(10));

        BorderPane playerAvatar = new BorderPane();
        playerAvatar.setCenter(new ImageView(map.getPlayer().getAvatar()));
        playerAvatar.setBackground(TOOLBOX_FILL_COLOR);

        infoLabel.setFont(LARGE_FONT);

        name.setFont(MEDIUM_FONT);
        name.setText(map.getPlayer().getName() + "(" + map.getPlayer().getAttack() + ")");
        healthLabel.setFont(SMALL_FONT);
        mannaLabel.setFont(SMALL_FONT);
        defenseLabel.setFont(SMALL_FONT);
        name.setTextFill(Color.WHITE);
        healthLabel.setTextFill(Color.WHITE);
        mannaLabel.setTextFill(Color.WHITE);
        defenseLabel.setTextFill(Color.WHITE);
        characterInfo.add(name, 0, 0);
        characterInfo.add(healthLabel, 0, 1);
        characterInfo.add(mannaLabel, 0, 2);
        characterInfo.add(defenseLabel, 0, 3);

        BorderPane weaponImage = new BorderPane();
        Label weaponKey = new Label("0");
        weaponKey.setFont(SMALL_FONT);
        weaponKey.setTextFill(Color.WHITE);
        BorderPane weapon = new BorderPane();
        weapon.setLeft(weaponKey);

        weaponImage.setCenter(new ImageView(map.getPlayer().getWeapon().getWeaponAvatar()));
        weapon.setRight(weaponImage);
        weaponAvatar.setCenter(weapon);
        characterInfo.add(weaponKey,1,0);
        characterInfo.add(weaponAvatar, 2, 0);

        for (int i = 0; i < map.getPlayer().getSpellList().size(); i++) {
            BorderPane spellImage = new BorderPane();
            spellImage.setCenter(new ImageView(map.getPlayer().getSpellList().get(i).getAvatarImage()));
            Label spellKey = new Label(" " + (i+1));
            spellKey.setFont(SMALL_FONT);
            BorderPane spell = new BorderPane();
            spellKey.setTextFill(Color.WHITE);
            spell.setLeft(spellKey);
            spell.setRight(spellImage);
            characterInfo.add(spell, i+2, 2);
        }

        enemyName.setFont(MEDIUM_FONT);
        enemyHealthLabel.setFont(SMALL_FONT);
        enemyMannaLabel.setFont(SMALL_FONT);
        enemyDefenseLabel.setFont(SMALL_FONT);
        enemyName.setTextFill(Color.WHITE);
        enemyHealthLabel.setTextFill(Color.WHITE);
        enemyMannaLabel.setTextFill(Color.WHITE);
        enemyDefenseLabel.setTextFill(Color.WHITE);
        enemyInfo.add(enemyName, 0, 0);
        enemyInfo.add(enemyHealthLabel, 0, 1);
        enemyInfo.add(enemyMannaLabel, 0, 2);
        enemyInfo.add(enemyDefenseLabel, 0, 3);

        BorderPane actorsStats = new BorderPane();
        actorsStats.setBackground(TOOLBOX_FILL_COLOR);
        actorsStats.setLeft(characterInfo);
        actorsStats.setRight(enemyInfo);
        infoLabel.setTextFill(Color.WHITE);
        actorsStats.setCenter(infoLabel);

        toolbar.setStyle("-fx-border-color : #ffffff; -fx-border-width : 5 0 ");
        toolbar.setLeft(playerAvatar);
        toolbar.setCenter(actorsStats);
        enemyAvatar.setBackground(TOOLBOX_FILL_COLOR);
        toolbar.setRight(enemyAvatar);
        toolbar.setMaxWidth(1920);
        return toolbar;
    }

    private void refresh() {
        final int VERTICAL_MAX = 20;
        final int HORIZONTAL_MAX = 30;

        int minX = Math.max(map.getPlayer().getX() - HORIZONTAL_MAX, 0);
        int minY = Math.max(map.getPlayer().getY() - VERTICAL_MAX, 0);
        int maxX = Math.min(map.getWidth(), map.getPlayer().getX() + HORIZONTAL_MAX);
        int maxY = Math.min(map.getHeight(), map.getPlayer().getY() + VERTICAL_MAX);

        if (minX == 0) maxX = Math.min(2 * HORIZONTAL_MAX + 1, map.getWidth() -1);
        if (minY == 0) maxY = Math.min(2 * VERTICAL_MAX + 1, map.getHeight() -1);
        if (maxX == map.getWidth() -1) minX = Math.max(map.getWidth() - 2 - HORIZONTAL_MAX * 2, 0);
        if (maxY == map.getHeight() -1) minY = Math.max(map.getHeight() - 2 - VERTICAL_MAX * 2, 0);

        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x - minX, y - minY);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x - minX, y - minY);
                } else {
                    Tiles.drawTile(context, cell, x - minX, y - minY);
                }
            }
        }

        name.setText(map.getPlayer().getName() + "(" + (map.getPlayer().getAttack() + map.getPlayer().getWeapon().getProperty()) + ")");
        weaponAvatar.setCenter(new ImageView(map.getPlayer().getWeapon().getWeaponAvatar()));

        healthLabel.setText("Health: " + map.getPlayer().getHealth());
        mannaLabel.setText("Manna: " + map.getPlayer().getMana());
        defenseLabel.setText("Defense: " + map.getPlayer().getDefense());
        infoLabel.setText("");
        if (map.getPlayer().standingOnItem()) {
            String itemName = map.getPlayer().getCell().getItem().getTileName();
            infoLabel.setText("Press [E] to pick up: " + itemName);
        }
        if (map.getPlayer().isThereEnemy()) {
            infoLabel.setText("PRESS [F] TO FIGHT!");
            enemyName.setText(map.getPlayer().getEnemy().getName() + "(" + map.getPlayer().getEnemy().getAttack() + ")");
            enemyHealthLabel.setText("Health:" + map.getPlayer().getEnemy().getHealth());
            enemyMannaLabel.setText("Manna:" + map.getPlayer().getEnemy().getMana());
            enemyDefenseLabel.setText("Defense:" + map.getPlayer().getEnemy().getDefense());
            enemyAvatar.setCenter(new ImageView(map.getPlayer().getEnemy().getAvatar()));
        } else {
            enemyName.setText("");
            enemyHealthLabel.setText("");
            enemyMannaLabel.setText("");
            enemyDefenseLabel.setText("");
            enemyAvatar.setCenter(null);
        }

    }

    private Canvas tutorial() {
        map = MapLoader.loadMap(0, player);
        map.getPlayer().setWeapon(new BareHand(map.getPlayer().getCell(), ItemType.WEAPON, 0));
        map.getPlayer().getCell().setItem(null);
        canvas = new Canvas(
                map.getWidth() * Tiles.TILE_WIDTH,
                map.getHeight() * Tiles.TILE_WIDTH);

        return canvas;
    }

    private Canvas level1() {
        map = MapLoader.loadMap(1, player);
        canvas = new Canvas(
                map.getWidth() * Tiles.TILE_WIDTH,
                map.getHeight() * Tiles.TILE_WIDTH);
        map.getPlayer().addSpell(Spell.BLIZZARD);
        return canvas;
    }

    private Canvas level2() {
        map = MapLoader.loadMap(2, player);
        canvas = new Canvas(
                map.getWidth() * Tiles.TILE_WIDTH,
                map.getHeight() * Tiles.TILE_WIDTH);
        map.getPlayer().addSpell(Spell.BLIZZARD);
        map.getPlayer().addSpell(Spell.METEOR);
        return canvas;
    }

    private void checkForStairs() {
        if (map.getPlayer().getCell().getType().equals(CellType.STAIRS)) {
            map.getPlayer().setMapLevel(map.getPlayer().getMapLevel() + 1);
            play(map.getPlayer().getMapLevel());
        }
    }

    private void update() {
        checkForStairs();
        map.updateActor();
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
